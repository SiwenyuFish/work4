package siwenyu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Result;
import siwenyu.service.CommentService;
import siwenyu.service.VideoService;

import java.util.List;

/**
 * 评论
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
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
           }
           else {
               if(commentService.searchById(commentId)==null)
                   return Result.error("评论失败，该评论不存在");
               Long id = videoService.publishByCommentId(commentId);
               commentService.publish(id,commentId,content);

               //该评论的子评论+1
               commentService.updateCount(commentId);

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

        Long id = videoService.publishByCommentId(commentId);

        if(videoId!=null)
            videoService.deleteComment(count,videoId);
        if(commentId!=null) {
            videoService.deleteComment(count, id);
            commentService.deleteComment(count,commentId);
        }

        commentService.delete(videoId,commentId);


        return Result.success();
    }

}
