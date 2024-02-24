package siwenyu.service;

import org.apache.ibatis.annotations.Select;
import siwenyu.pojo.Friend;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.PagePojo;

public interface RelationService {
    void action( Long id,Long userId, Integer actionType);

    PageBean<Friend> list(Long userId, Integer pageNum, Integer pageSize);


    PagePojo<Friend> list(Long userId);


    PageBean<Friend> fanslist(Long userId, Integer pageNum, Integer pageSize);

    PagePojo<Friend> fanslist(Long userId);

    PageBean<Friend> friendslist(Long userId, Integer pageNum, Integer pageSize);

    PagePojo<Friend> friendslist(Long userId);
}
