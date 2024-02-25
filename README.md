# work4

# [接口文档](https://apifox.com/apidoc/shared-6c1bc6c1-6281-461f-a5b5-64a326928391/api-149143987)
# 项目结构

│  .gitignore
│  pom.xml
│  README.md
│  
├─.idea
│  │  .gitignore
│  │  compiler.xml
│  │  dataSources.local.xml
│  │  dataSources.xml
│  │  encodings.xml
│  │  jarRepositories.xml
│  │  misc.xml
│  │  sqldialects.xml
│  │  uiDesigner.xml
│  │  vcs.xml
│  │  workspace.xml
│  │
│  └─dataSources
│      │  2f6569b5-b94a-4c3e-bab0-442402cb2c88.xml
│      │  674b8423-c71d-4265-b838-b7c9eb25d6fb.xml
│      │  8d7d968b-8364-497d-8de2-582930902f67.xml
│      │
│      └─2f6569b5-b94a-4c3e-bab0-442402cb2c88
│          └─storage_v2
│              └─_src_
│                  └─schema
│                          information_schema.FNRwLQ.meta
│                          mysql.osA4Bg.meta
│                          mywebsite.b1mLgw.meta
│                          performance_schema.kIw0nw.meta
│                          sys.zb4BAA.meta
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
│  │  │      │      PageBean.java
│  │  │      │      PagePojo.java
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
│
└─target
    ├─classes
    │  │  application.yml
    │  │
    │  ├─mybatis
    │  │  └─mapper
    │  │          CommentMapper.xml
    │  │          UserMapper.xml
    │  │          VideoMapper.xml
    │  │
    │  └─siwenyu
    │      │  Demo10Application.class
    │      │
    │      ├─config
    │      │      RedisConfig.class
    │      │      WebConfig.class
    │      │
    │      ├─controller
    │      │      CommentController.class
    │      │      LikeController.class
    │      │      RelationController.class
    │      │      UserController.class
    │      │      VideoController.class
    │      │
    │      ├─exception
    │      │      ExceptionHandler.class
    │      │
    │      ├─interceptor
    │      │      LoginInterceptor.class
    │      │
    │      ├─mapper
    │      │      CommentMapper.class
    │      │      LikeMapper.class
    │      │      RelationMapper.class
    │      │      UserMapper.class
    │      │      VideoMapper.class
    │      │
    │      ├─pojo
    │      │      Comment.class
    │      │      Friend.class
    │      │      Like.class
    │      │      PageBean.class
    │      │      PagePojo.class
    │      │      Relation.class
    │      │      Result.class
    │      │      User.class
    │      │      Video.class
    │      │
    │      ├─service
    │      │  │  CommentService.class
    │      │  │  LikeService.class
    │      │  │  RelationService.class
    │      │  │  UserService.class
    │      │  │  VideoService.class
    │      │  │
    │      │  └─impl
    │      │          CommentServiceImpl.class
    │      │          LikeServiceImpl.class
    │      │          RelationServiceImpl.class
    │      │          UserServiceImpl.class
    │      │          VideoServiceImpl.class
    │      │
    │      └─utils
    │              AliOssUtil.class
    │              JwtUtil.class
    │              SnowFlakeUtil.class
    │              ThreadLocalUtil.class
    │
    ├─generated-sources
    │  └─annotations
    ├─generated-test-sources
    │  └─test-annotations
    └─test-classes
        └─siwenyu
                Demo10ApplicationTests.class
