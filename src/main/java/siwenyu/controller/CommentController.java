package siwenyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Result;
import siwenyu.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/publish")
    public Result publish(@RequestParam(required = false) Long videoId, @RequestParam(required = false) Long commentId, @RequestParam(required = false) String content){

       if(videoId!=null||commentId!=null){
           commentService.publish(videoId,commentId,content);
           return Result.success();
       }
        return Result.error("至少有一个参数为非空");
    }

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

    @DeleteMapping("/delete")
    public Result delete(@RequestParam(required = false) Long videoId,@RequestParam(required = false) Long commentId){
        if(videoId==null&&commentId==null){
            return Result.error("至少有一个参数为非空");
        }
        commentService.delete(videoId,commentId);
        return Result.success();
    }

}
