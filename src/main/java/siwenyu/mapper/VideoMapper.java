package siwenyu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

}
