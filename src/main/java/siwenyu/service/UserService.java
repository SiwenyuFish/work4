package siwenyu.service;

import org.springframework.stereotype.Service;
import siwenyu.pojo.User;


public interface UserService {

    /**
     * 实现输出登录用户信息
     */
    User findByUserName(String username);

    /**
     * 实现用户注册 将用户信息保存到数据库
     */
    void register(String username, String password);

    /**
     * 实现用户头像上传 将用户头像信息保存到数据库
     */
    void updateAvatar(String avatarUrl);
}
