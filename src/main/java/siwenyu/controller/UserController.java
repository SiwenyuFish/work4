package siwenyu.controller;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import siwenyu.pojo.Result;
import siwenyu.pojo.User;
import siwenyu.service.UserService;
import siwenyu.utils.AliOssUtil;
import siwenyu.utils.JwtUtil;
import siwenyu.utils.SnowFlakeUtil;
import java.util.HashMap;
import java.util.Map;



/**
 * 用户
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册 用户名必须为2-16个字符，密码必须为6-16个字符
     * 用户名唯一
     */
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

    /**
     * 登录 使用JwtUtil获取token
     */
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

    /**
     * 输出指定id用户信息
     */
    @GetMapping("/info")
    public Result<User> userInfo(Long userId) {
        User user = userService.findByUserId(userId);
        return Result.success(user);
    }

    /**
     * 上传用户头像
     */

   @PutMapping("/updateAvatar")
    public Result updateAvatar(MultipartFile file) throws Exception {
       String originalFilename = file.getOriginalFilename();

       //保证文件的名字是唯一的,从而防止文件覆盖
       String filename = SnowFlakeUtil.getSnowFlakeId() + originalFilename.substring(originalFilename.lastIndexOf("."));

       String url = AliOssUtil.uploadFile(filename, file.getInputStream());

       userService.updateAvatar(url);

       return Result.success(url);
    }



}
