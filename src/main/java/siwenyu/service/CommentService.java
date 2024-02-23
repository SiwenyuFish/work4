package siwenyu.service;

import siwenyu.pojo.Comment;
import siwenyu.pojo.PagePojo;

public interface CommentService {
    void publish(Long videoId, Long commentId, String content);

    PagePojo<Comment> list(Long videoId, Long commentId, Integer pageNum, Integer pageSize);

    PagePojo<Comment> list(Long videoId, Long commentId);
}