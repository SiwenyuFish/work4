package siwenyu.controller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import siwenyu.pojo.Result;
import siwenyu.pojo.User;
import siwenyu.service.UserService;
import siwenyu.utils.JwtUtil;
import siwenyu.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{2,16}$") String username, @Pattern(regexp = "^\\S{6,16}$") String password){
        User user=userService.findByUserName(username);
        if(user==null){
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{2,16}$") String username, @Pattern(regexp = "^\\S{6,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        //判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确  loginUser对象中的password是密文
        if (password.equals(loginUser.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        //根据用户名查询用户

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);
        return Result.success(user);
    }

   @PutMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }



}
