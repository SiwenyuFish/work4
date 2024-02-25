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

    @Select("select * from video where user_id =#{userId}")
    List<Video> list(Long userId);

    List<Video> search(String keywords, String fromDate, String toDate);


    @Update("update video set like_count=like_count+1 ,video.visit_count=video.visit_count+1 where id =#{id}")
    void actionLike(String id);

    @Update("update video set like_count=like_count-1 where id =#{id}")
    void actionDislike(String id);

    @Select("select * from video where id=#{id}")
    Video searchById(String id);
}
