package siwenyu.service;

import siwenyu.pojo.PageBean;
import siwenyu.pojo.Video;

import java.util.List;


public interface VideoService {
    void upload(String url,String title, String describe);

    PageBean<Video> list(Long userId, Integer pageNum, Integer pageSize);

    PageBean<Video> search(String keywords, Integer pageNum, Integer pageSize, String fromDate, String toDate);

    List<Video> popular(Integer pageNum, Integer pageSize);

    void action(String id, Integer actionType);
}
