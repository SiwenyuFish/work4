package siwenyu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import siwenyu.pojo.Friend;

import java.util.List;

@Mapper
@Repository
public interface RelationMapper {

    @Insert("insert into relation (user_id, follower_id) VALUES (#{userId},#{id})")
    void actionFollow(Long id, Long userId);

    @Delete("delete from relation where user_id=#{userId} and follower_id=#{id}")
    void actionUnfollow(Long id, Long userId);


    @Select("select user.id,user.username,user.avatar_url FROM user inner join relation r on user.id = r.user_id where follower_id=#{userId}")
    List<Friend> list(Long userId);

    @Select("select user.id,user.username,user.avatar_url from user inner join relation r on user.id = r.follower_id where user_id=#{userId}")
    List<Friend> fanslist(Long userId);

    @Select("select user.id,user.username,user.avatar_url from user inner join relation r1 on user.id = r1.follower_id inner join relation r2 on r1.user_id=r2.follower_id and r1.follower_id=r2.user_id where r1.user_id=#{userId}")
    List<Friend> friendslist(Long userId);
}
