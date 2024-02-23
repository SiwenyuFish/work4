package siwenyu.service;

import siwenyu.pojo.PagePojo;
import siwenyu.pojo.Video;

public interface LikeService {
    void videoAction(String id,  Long userId, Integer actionType);

    PagePojo<Video> list(Long userId, Integer pageNum, Integer pageSize);
    PagePojo<Video> list(Long userId);
}
