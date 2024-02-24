package siwenyu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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
    void actionLike(String id);

    @Update("update comment set like_count=like_count-1 where id = #{id}")
    void actionDislike(String id);
}
