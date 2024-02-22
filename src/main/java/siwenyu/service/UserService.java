package siwenyu.service;

import org.springframework.stereotype.Service;
import siwenyu.pojo.User;


public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void updateAvatar(String avatarUrl);
}
