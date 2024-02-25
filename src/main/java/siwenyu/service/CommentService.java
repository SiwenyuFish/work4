package siwenyu.service;

import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;

public interface CommentService {
    void publish(Long videoId, Long commentId, String content);

    MyPageBean<Comment> list(Long videoId, Long commentId, Integer pageNum, Integer pageSize);

    MyPageBean<Comment> list(Long videoId, Long commentId);

    void delete(Long videoId, Long commentId);


    void action(String id, Integer actionType);
}
