package siwenyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Result;
import siwenyu.pojo.Video;
import siwenyu.service.CommentService;
import siwenyu.service.LikeService;
import siwenyu.service.VideoService;
import siwenyu.utils.ThreadLocalUtil;

import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoService videoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @PostMapping("/action")
    public Result action(@RequestParam(required = false) String videoId, @RequestParam(required = false) String commentId, Integer actionType){
        if(actionType==1){
            if(videoId!=null) {
                return actionVideoLike(videoId, actionType);
            }
            if(commentId!=null){
                return actionCommentLike(commentId,actionType);
            }
            else {
                return Result.error("至少有一个参数为非空");
            }
        }else if(actionType==2){
            if(videoId!=null) {
                return actionVideoDislike(videoId, actionType);
            }
            if(commentId!=null){
                return actionCommentDislike(commentId,actionType);
            }
            else {
                return Result.error("至少有一个参数为非空");
            }
        }else {
            return Result.error("点赞操作类型必须为1或者2");
        }
    }

    public Result actionVideoLike(String id,Integer actionType){
        Map<String,Object> map1 = ThreadLocalUtil.get();
        Long userId = (Long) map1.get("id");
        String key=userId+"like:"+id;
        long result = redisTemplate.opsForSet().add(key,id);
        if(result>0) {
            videoService.action(id,actionType);
            likeService.videoAction(id,userId,actionType);
            //每点赞一次点击量＋1

            double result2;
            try {
                result2 = redisTemplate.opsForZSet().score("chart",id);
                redisTemplate.opsForZSet().add("chart",id,result2+1);
            } catch (Exception e) {
                redisTemplate.opsForZSet().add("chart",id,1);
            }finally {
                Video video=videoService.searchById(id);
                redisTemplate.opsForHash().put("HashVideo",id,video);

                return Result.success("点赞成功");
            }


        }else {
            return Result.error("不能重复点赞");
        }
    }

    public Result actionVideoDislike(String id,Integer actionType){
        Map<String,Object> map1 = ThreadLocalUtil.get();
        Long userId = (Long) map1.get("id");
        String key=userId+"like:"+id;
        long result = redisTemplate.opsForSet().remove(key,id);
        if(result>0) {
            videoService.action(id, actionType);
            likeService.videoAction(id,userId,actionType);
            return Result.success("取消点赞成功");
        }else {
            return Result.error("未进行点赞");
        }
    }

    public Result actionCommentLike(String id,Integer actionType){
        Map<String,Object> map1 = ThreadLocalUtil.get();
        Long userId = (Long) map1.get("id");
        String key=userId+"commentLike:"+id;
        long result = redisTemplate.opsForSet().add(key,id);
        if(result>0) {
            commentService.action(id,actionType);
            likeService.commentAction(id,userId,actionType);
            return Result.success("点赞成功");
        }else {
            return Result.error("不能重复点赞");
        }
    }

    public Result actionCommentDislike(String id,Integer actionType){
        Map<String,Object> map1 = ThreadLocalUtil.get();
        Long userId = (Long) map1.get("id");
        String key=userId+"commentLike:"+id;
        long result = redisTemplate.opsForSet().remove(key,id);
        if(result>0) {
            commentService.action(id, actionType);
            likeService.commentAction(id,userId,actionType);
            return Result.success("取消点赞成功");
        }else {
            return Result.error("未进行点赞");
        }
    }

    @GetMapping("/list")
    public Result<MyPageBean<Video>> list(Long userId, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        if(pageNum!=null&&pageSize!=null) {
            MyPageBean<Video> pp = likeService.list(userId, pageNum, pageSize);
            return Result.success(pp);
        }else {
            MyPageBean<Video> pp = likeService.list(userId);
            return Result.success(pp);
        }
    }

}
