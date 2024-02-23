package siwenyu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import siwenyu.pojo.Video;

import java.util.List;

@Mapper
@Repository
public interface LikeMapper {

    @Insert("insert into `like`(user_id, video_id) VALUES(#{userId},#{id}) ")
    void actionVideoLike(String id, Long userId);

   @Delete("delete from `like` where user_id=#{userId} and video_id=#{id}")
    void actionVideoDislike(String id, Long userId);


   @Select("select id,v.user_id,video_url,cover_url,title,description,visit_count,like_count"+
           ",comment_count,created_at,updated_at,deleted_at from video v inner join `like` l " +
           "on v.id = l.video_id where l.user_id=#{userId}")
    List<Video> list(Long userId);
}