package siwenyu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Result;
import siwenyu.pojo.Video;
import siwenyu.service.CommentService;
import siwenyu.service.VideoService;

import java.util.List;

/**
 * 评论
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private CommentService commentService;

    @Autowired
    private VideoService videoService;

    /**
     * 评论操作
     */
    @SaCheckLogin
    @PostMapping("/publish")
    public Result publish(@RequestParam(required = false) Long videoId, @RequestParam(required = false) Long commentId, @RequestParam(required = false) String content){

       if(videoId!=null||commentId!=null){
           if(videoId!=null){
               if(videoService.searchById(videoId)==null)
                   return Result.error("评论失败，该视频不存在");
               videoService.publishByVideoId(videoId);
               commentService.publish(videoId,commentId,content);

               //更新redis
               Video video=videoService.searchById(videoId);
               redisTemplate.opsForHash().put("HashVideo",videoId,video);
           }
           else {
               if(commentService.searchById(commentId)==null)
                   return Result.error("评论失败，该评论不存在");
               Long id = videoService.publishByCommentId(commentId);
               commentService.publish(id,commentId,content);

               //该评论的子评论+1
               commentService.updateCount(commentId);

               //更新redis
               Video video=videoService.searchById(id);
               redisTemplate.opsForHash().put("HashVideo",id,video);

           }


           return Result.success();
       }
        return Result.error("至少有一个参数为非空");
    }

    /**
     *输出评论列表 按视频编号和评论编号查询
     */
    @SaCheckLogin
    @GetMapping("/list")
    public Result<MyPageBean<Comment>> list(@RequestParam(required = false) Long videoId, @RequestParam(required = false) Long commentId, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
        if (videoId == null && commentId == null) {
            return Result.error("至少有一个参数为非空");
        }
        if (pageNum != null && pageSize != null) {
            MyPageBean<Comment> pp = commentService.list(videoId, commentId, pageNum, pageSize);
            return Result.success(pp);
        } else {
            MyPageBean<Comment> pp = commentService.list(videoId, commentId);
            return Result.success(pp);
        }
    }

    /**
     *删除评论 按视频编号和评论编号查询
     */
    @SaCheckLogin
    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = false) Long videoId,@RequestParam(required = false) Long commentId){
        if(videoId==null&&commentId==null){
            return Result.error("至少有一个参数为非空");
        }
        if(videoId!=null)
            if(videoService.searchById(videoId)==null)
                return Result.error("删除评论失败，该视频不存在");
        if(commentId!=null)
            if(commentService.searchById(commentId)==null)
                return Result.error("删除评论失败，该评论不存在");

        List<Comment> comments = commentService.listComment(videoId, commentId);
        int count=comments.size();
        log.info("count的值为"+count);

        Long id = videoService.searchByCommentId(commentId);

        if(videoId!=null&&commentId==null) {
            videoService.deleteComment(count, videoId);
            //更新redis
            Video video=videoService.searchById(videoId);
            redisTemplate.opsForHash().put("HashVideo",videoId,video);
        }
        else {
            //获取删除评论的子评论
            List<Comment> comments1 = commentService.listChildComment(commentId);
            count+=comments1.size();
            log.info("count的值为"+count);
            //删除子评论
            commentService.delete(commentId);

            //更新视频表和评论表的数据
            videoService.deleteComment(count, id);
            commentService.deleteComment(count,commentId);

            //更新redis
            Video video=videoService.searchById(id);
            redisTemplate.opsForHash().put("HashVideo",id,video);
        }

        //删除符合条件的评论
        commentService.delete(videoId,commentId);

        return Result.success();
    }

}
