package siwenyu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import siwenyu.pojo.Comment;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    @Insert("insert into comment (id,user_id,video_id,parent_id,content,created_at,updated_at,deleted_at)"+"" +
            "values (#{id},#{userId},#{videoId},#{commentId},#{content},now(),now(),now())")
    void publish(Long id, Long userId, Long videoId, Long commentId, String content);


    List<Comment> list(Long videoId, Long commentId);

    void delete(Long videoId, Long commentId);

    @Update("update comment set like_count=like_count+1 where id = #{id}")
    void actionLike(Long id);

    @Update("update comment set like_count=like_count-1 where id = #{id}")
    void actionDislike(Long id);

    @Select("select comment.video_id from comment where id =#{commentId}")
    Long findByVideoId(Long commentId);

    @Select("select comment.id,comment.user_id,comment.video_id,comment.parent_id,comment.like_count,comment.child_count,comment.content,comment.created_at,comment.updated_at,comment.deleted_at from comment where id =#{commentId}")
    Comment searchById(Long commentId);

    @Update("update comment set child_count=child_count+1 where id =#{commentId}")
    void updateCount(Long commentId);

    @Update("update comment set child_count=child_count-#{count} where id =#{commentId}")
    void deleteComment(int count, Long commentId);
}
