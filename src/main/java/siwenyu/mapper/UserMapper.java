package siwenyu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import siwenyu.pojo.User;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    @Select("select user.id,user.username,password,user.avatar_url,user.created_at,user.updated_at,user.deleted_at from user where id=#{userId}")
    User findByUserId(Long userId);


    @Select("select user.id,user.username,password,user.avatar_url,user.created_at,user.updated_at,user.deleted_at from user where username=#{username}")
    User findByUserName(String username);

    void add(Map<String,Object> map);

    @Update("update user set avatar_url=#{avatarUrl},updated_at=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Long id);


}
