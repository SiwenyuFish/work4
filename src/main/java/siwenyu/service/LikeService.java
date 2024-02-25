package siwenyu.service;

import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Video;

public interface LikeService {
    void videoAction(String id,  Long userId, Integer actionType);

    MyPageBean<Video> list(Long userId, Integer pageNum, Integer pageSize);
    MyPageBean<Video> list(Long userId);


    void commentAction(String id, Long userId, Integer actionType);
}
