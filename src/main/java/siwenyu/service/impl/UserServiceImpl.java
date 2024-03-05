package siwenyu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.UserMapper;
import siwenyu.pojo.User;
import siwenyu.service.UserService;
import siwenyu.utils.SnowFlakeUtil;
import siwenyu.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 实现输出登录用户信息
     */
    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }


    /**
     * 实现用户注册 将用户信息保存到数据库
     */
    @Override
    public void register(String username, String password) {
        HashMap<String, Object>map=new HashMap<String,Object>();
        map.put("id", SnowFlakeUtil.getSnowFlakeId());
        map.put("username",username);
        map.put("password",password);
        userMapper.add(map);
    }

    /**
     * 实现用户头像上传 将用户头像信息保存到数据库
     */

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);

    }

    /**
     * 通过用户id查询用户
     */
    @Override
    public User findByUserId(Long userId) {
        return userMapper.findByUserId(userId);
    }
}
