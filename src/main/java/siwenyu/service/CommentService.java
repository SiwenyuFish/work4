package siwenyu.service;

import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;

import java.util.List;

public interface CommentService {

    /**
     * 实现发表评论的操作 数据更新到comment表
     */
    void publish(Long videoId, Long commentId, String content);

    /**
     * 分页查询指定视频下的评论或者指定评论下的子评论
     */
    MyPageBean<Comment> list(Long videoId, Long commentId, Integer pageNum, Integer pageSize);

    /**
     * 不分页查询指定视频下的评论或者指定评论下的子评论
     */
    MyPageBean<Comment> list(Long videoId, Long commentId);

    /**
     * 删除指定视频id或者评论id下的评论
     */
    void delete(Long videoId, Long commentId);

    /**
     * 实现给评论点赞的操作 数据更新到comment表
     */
    void action(Long id, Integer actionType);

    Comment searchById(Long commentId);

    List<Comment> listComment(Long videoId, Long commentId);

    void updateCount(Long commentId);

    void deleteComment(int count, Long commentId);

    List<Comment> listChildComment(Long commentId);

    /**
     * 删除指定评论的子评论
     */
    void delete(Long commentId);
}