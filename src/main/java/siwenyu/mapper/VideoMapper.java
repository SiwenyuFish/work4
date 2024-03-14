package siwenyu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import siwenyu.pojo.Video;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface VideoMapper {
    void upload(Map<String,Object> map);

    @Select("select id,user_id,video_url,cover_url,title,description,visit_count,like_count,comment_count,created_at,updated_at,deleted_at from video where user_id =#{userId}")
    List<Video> list(Long userId);

    List<Video> search(String keywords, String fromDate, String toDate);


    @Update("update video set like_count=like_count+1 ,video.visit_count=video.visit_count+1 where id =#{id}")
    void actionLike(Long id);

    @Update("update video set like_count=like_count-1 where id =#{id}")
    void actionDislike(Long id);

    @Select("select id,user_id,video_url,cover_url,title,description,visit_count,like_count,comment_count,created_at,updated_at,deleted_at from video where id=#{id}")
    Video searchById(Long id);

    @Update("update video set comment_count=comment_count+1 where id =#{videoId}")
    void publishByVideoId(Long videoId);


    @Update("update video set  comment_count=comment_count-#{count} where id = #{videoId}")
    void deleteComment(int count, Long videoId);
}
