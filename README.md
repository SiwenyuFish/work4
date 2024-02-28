# work4
# Bonus
# 使用阿里云oss存储投稿的视频文件
# [接口文档](https://apifox.com/apidoc/shared-6c1bc6c1-6281-461f-a5b5-64a326928391/api-149143987)
# 项目结构

    │  .gitignore
    │  Dockerfile
    │  pom.xml
    │  README.md
    │
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─siwenyu
    │  │  │      │  Demo10Application.java
    │  │  │      │
    │  │  │      ├─config
    │  │  │      │      RedisConfig.java
    │  │  │      │      WebConfig.java
    │  │  │      │
    │  │  │      ├─controller
    │  │  │      │      CommentController.java
    │  │  │      │      LikeController.java
    │  │  │      │      RelationController.java
    │  │  │      │      UserController.java
    │  │  │      │      VideoController.java
    │  │  │      │
    │  │  │      ├─exception
    │  │  │      │      ExceptionHandler.java
    │  │  │      │
    │  │  │      ├─interceptor
    │  │  │      │      LoginInterceptor.java
    │  │  │      │
    │  │  │      ├─mapper
    │  │  │      │      CommentMapper.java
    │  │  │      │      LikeMapper.java
    │  │  │      │      RelationMapper.java
    │  │  │      │      UserMapper.java
    │  │  │      │      VideoMapper.java
    │  │  │      │
    │  │  │      ├─pojo
    │  │  │      │      Comment.java
    │  │  │      │      Friend.java
    │  │  │      │      Like.java
    │  │  │      │      MyPageBean.java
    │  │  │      │      PageBean.java
    │  │  │      │      Relation.java
    │  │  │      │      Result.java
    │  │  │      │      User.java
    │  │  │      │      Video.java
    │  │  │      │
    │  │  │      ├─service
    │  │  │      │  │  CommentService.java
    │  │  │      │  │  LikeService.java
    │  │  │      │  │  RelationService.java
    │  │  │      │  │  UserService.java
    │  │  │      │  │  VideoService.java
    │  │  │      │  │
    │  │  │      │  └─impl
    │  │  │      │          CommentServiceImpl.java
    │  │  │      │          LikeServiceImpl.java
    │  │  │      │          RelationServiceImpl.java
    │  │  │      │          UserServiceImpl.java
    │  │  │      │          VideoServiceImpl.java
    │  │  │      │
    │  │  │      └─utils
    │  │  │              AliOssUtil.java
    │  │  │              JwtUtil.java
    │  │  │              SnowFlakeUtil.java
    │  │  │              ThreadLocalUtil.java
    │  │  │
    │  │  └─resources
    │  │      │  application.yml
    │  │      │  logback-spring.xml
    │  │      │
    │  │      ├─mybatis
    │  │      │  └─mapper
    │  │      │          CommentMapper.xml
    │  │      │          UserMapper.xml
    │  │      │          VideoMapper.xml
    │  │      │
    │  │      ├─static
    │  │      └─templates
    │  └─test
    │      └─java
    │          └─siwenyu
    │                  Demo10ApplicationTests.java
    

    
    
