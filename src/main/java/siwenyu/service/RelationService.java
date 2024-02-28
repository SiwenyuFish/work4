package siwenyu.service;

import siwenyu.pojo.Friend;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.MyPageBean;

public interface RelationService {

    /**
     * 实现用户关注的操作 数据保存到relation表
     */
    void action( Long id,Long userId, Integer actionType);

    /**
     * 实现分页查询指定用户的关注列表
     */
    PageBean<Friend> list(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 实现查询指定用户的关注列表
     */
    MyPageBean<Friend> list(Long userId);

    /**
     * 实现分页查询指定用户的粉丝列表
     */
    PageBean<Friend> fanslist(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 实现查询指定用户的粉丝列表
     */
    MyPageBean<Friend> fanslist(Long userId);

    /**
     * 实现分页查询指定用户的好友列表
     */
    PageBean<Friend> friendslist(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 实现查询指定用户的好友列表
     */
    MyPageBean<Friend> friendslist(Long userId);
}
