package siwenyu.service;

import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Video;

public interface LikeService {

    /**
     * 实现给视频点赞的操作 数据保存到like表
     */
    void videoAction(Long id, Long userId, Integer actionType);

    /**
     * 分页查询指定用户点赞的视频列表
     */
    MyPageBean<Video> list(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 不分页查询指定用户点赞的视频列表
     */
    MyPageBean<Video> list(Long userId);

    /**
     * 实现给评论点赞的操作 数据保存到like表
     */
    void commentAction(Long id, Long userId, Integer actionType);
}
