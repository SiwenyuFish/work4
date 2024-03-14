package siwenyu.service;

import siwenyu.pojo.PageBean;
import siwenyu.pojo.Video;


public interface VideoService {

    /**
     * 实现视频上传
     */
    void upload(String url,String title, String describe);

    /**
     * 实现分页查询视频列表
     */
    PageBean<Video> list(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 实现关键字搜索视频列表
     */
    PageBean<Video> search(String keywords, Integer pageNum, Integer pageSize, String fromDate, String toDate);

    /**
     * 给视频点赞
     */
    void action(Long id, Integer actionType);

    /**
     * 给视频取消点赞
     */
    Video searchById(Long id);

    /**
     * 通过视频编号给视频评论后更新视频评论数
     */
    void publishByVideoId(Long videoId);

    /**
     * 通过评论编号给视频评论后更新视频评论数
     */
    Long publishByCommentId(Long commentId);

    void deleteComment(int count, Long videoId);
}
