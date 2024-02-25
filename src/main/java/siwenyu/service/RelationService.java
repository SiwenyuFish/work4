package siwenyu.service;

import siwenyu.pojo.Friend;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.MyPageBean;

public interface RelationService {
    void action( Long id,Long userId, Integer actionType);

    PageBean<Friend> list(Long userId, Integer pageNum, Integer pageSize);


    MyPageBean<Friend> list(Long userId);


    PageBean<Friend> fanslist(Long userId, Integer pageNum, Integer pageSize);

    MyPageBean<Friend> fanslist(Long userId);

    PageBean<Friend> friendslist(Long userId, Integer pageNum, Integer pageSize);

    MyPageBean<Friend> friendslist(Long userId);
}
