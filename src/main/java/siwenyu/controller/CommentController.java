package siwenyu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Result;
import siwenyu.service.CommentService;

/**
 * 评论
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 评论操作
     */
    @SaCheckLogin
    @PostMapping("/publish")
    public Result publish(@RequestParam(required = false) Long videoId, @RequestParam(required = false) Long commentId, @RequestParam(required = false) String content){

       if(videoId!=null||commentId!=null){
           commentService.publish(videoId,commentId,content);
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
        commentService.delete(videoId,commentId);
        return Result.success();
    }

}
