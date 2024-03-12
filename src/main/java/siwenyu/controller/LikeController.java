package siwenyu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
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

/**
 * 点赞
 */
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

    /**
     * 点赞操作 给视频或者评论点赞
     */

    @SaCheckLogin
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

    /**
     * 给视频点赞
     * 先查询redis是否存在点赞信息，如果有，则直接返回不能重复点赞
     * 否则将点赞信息放入redis并更新数据库
     * 成功进行点赞操作后 点击量＋1
     * 在redis中创建一个名为chart的ZSet 存放视频编号和点击量
     * 在redis中创建一个名为HashVideo的Hash表 存放视频编号和视频的信息
     */

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
                //出现异常 说明这个视频从未被点击过
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

    /**
     * 给视频取消点赞
     * 先查询redis是否存在点赞信息，如果没有，则直接返回未进行点赞
     * 否则将点赞信息从redis中删除并更新数据库
     */

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

    /**
     * 给评论点赞
     * 先查询redis是否存在点赞信息，如果有，则直接返回不能重复点赞
     * 否则将点赞信息放入redis并更新数据库
     */
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

    /**
     * 给评论取消点赞
     * 先查询redis是否存在点赞信息，如果没有，则直接返回未进行点赞
     * 否则将点赞信息从redis中删除并更新数据库
     */

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

    /**
     * 查看指定用户的点赞列表
     */

    @SaCheckLogin
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
