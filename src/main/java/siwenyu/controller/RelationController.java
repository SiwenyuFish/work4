package siwenyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import siwenyu.pojo.Friend;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.PagePojo;
import siwenyu.pojo.Result;
import siwenyu.service.RelationService;
import siwenyu.utils.ThreadLocalUtil;

import java.util.Map;

@RestController
public class RelationController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RelationService relationService;


    @PostMapping("/relation/action")
    public Result action(Long userId,Integer actionType){
        if(actionType==0){
            return actionFollow(userId,actionType);
        }else if(actionType==1){
            return actionUnfollow(userId,actionType);
        }else {
            return Result.error("关注操作类型必须为0或者1");
        }
    }

    public Result actionFollow(Long userId,Integer actionType){
        Map<String,Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String key=id +"following"+userId;
        long result = redisTemplate.opsForSet().add(key,id);
        if(result>0) {
            relationService.action(id,userId,actionType);
            return Result.success("关注成功");
        }else {
            return Result.error("不能重复关注");
        }
    }
    public Result actionUnfollow(Long userId,Integer actionType){
        Map<String,Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String key=id +"following"+userId;
        long result = redisTemplate.opsForSet().remove(key,id);
        if(result>0) {
            relationService.action(id,userId,actionType);
            return Result.success("取消关注成功");
        }else {
            return Result.error("未进行关注");
        }
    }


    @GetMapping("/following/list")
    public Result followingList(Long userId, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        if(pageNum!=null&&pageSize!=null){
            return followingListPage(userId,pageNum,pageSize);
        }
        else {
            return followingListNoPage(userId);
        }
    }

    public Result<PageBean<Friend>> followingListPage(Long userId,Integer pageNum,Integer pageSize){
        PageBean<Friend> friendPageBean = relationService.list(userId, pageNum, pageSize);
        return Result.success(friendPageBean);
    }

    public Result<PagePojo<Friend>> followingListNoPage(Long userId){
        PagePojo<Friend> friendPagePojo = relationService.list(userId);
        return Result.success(friendPagePojo);
    }

    @GetMapping("/follower/list")
    public Result followerList(Long userId, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        if(pageNum!=null&&pageSize!=null){
            return followerListPage(userId,pageNum,pageSize);
        }
        else {
            return followerListNoPage(userId);
        }
    }

    public Result<PageBean<Friend>> followerListPage(Long userId,Integer pageNum,Integer pageSize){
        PageBean<Friend> friendPageBean = relationService.fanslist(userId, pageNum, pageSize);
        return Result.success(friendPageBean);
    }

    public Result<PagePojo<Friend>> followerListNoPage(Long userId){
        PagePojo<Friend> friendPagePojo = relationService.fanslist(userId);
        return Result.success(friendPagePojo);
    }

    @GetMapping("/friends/list")
    public Result friendsList(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize){
        Map<String,Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        if(pageNum!=null&&pageSize!=null){
            return friendsListPage(id,pageNum,pageSize);
        }
        else {
            return friendsListNoPage(id);
        }
    }

    public Result<PageBean<Friend>> friendsListPage(Long userId,Integer pageNum,Integer pageSize){
        PageBean<Friend> friendPageBean = relationService.friendslist(userId, pageNum, pageSize);
        return Result.success(friendPageBean);
    }

    public Result<PagePojo<Friend>> friendsListNoPage(Long userId){
        PagePojo<Friend> friendPagePojo = relationService.friendslist(userId);
        return Result.success(friendPagePojo);
    }

}
