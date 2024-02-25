package siwenyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.Result;
import siwenyu.pojo.Video;
import siwenyu.service.VideoService;
import siwenyu.utils.AliOssUtil;
import siwenyu.utils.SnowFlakeUtil;

import java.util.List;
import java.util.Set;

/**
 * 视频
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * 上传视频 通过阿里云oss存储上传的视频文件
     * @param file 视频文件
     * @param title 视频标题
     * @param description 视频描述
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, @RequestParam(required = false) String title, @RequestParam(required = false) String description) throws Exception {

        String originalFilename = file.getOriginalFilename();

        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = SnowFlakeUtil.getSnowFlakeId() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = AliOssUtil.uploadFile(filename, file.getInputStream());

        videoService.upload(url, title, description);

        return Result.success(url);
    }

    /**
     * 查询指定用户投稿的视频
     */

    @GetMapping("/list")
    public Result<PageBean<Video>> list(Long userId, Integer pageNum, Integer pageSize) {
        PageBean<Video> pb = videoService.list(userId, pageNum, pageSize);
        return Result.success(pb);
    }

    /**
     * 热门排行榜
     * ZSet：chart 视频id 点击量
     * Hash：HashVideo 视频id 视频信息
     * @return 点击量视频信息从高到低排序
     */
    @GetMapping("/popular")
    public Result<List<Video>> popular(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize ){
        if(pageNum!=null&&pageSize!=null){
            Set chartPage = redisTemplate.opsForZSet().reverseRange("chart", pageNum-1, pageSize);
            List<Video> videos=redisTemplate.opsForHash().multiGet("HashVideo",chartPage);
            return Result.success(videos);
        }
        else {
            Set chart = redisTemplate.opsForZSet().reverseRange("chart", 0, -1);
            List<Video> videos = redisTemplate.opsForHash().multiGet("HashVideo", chart);
            return Result.success(videos);
        }
    }

    /**
     *
     * @param keywords 视频关键词
     * @param fromDate 最早视频投稿日期
     * @param toDate 最晚视频投稿日期
     * @return 符合查询条件的视频
     */
    @PostMapping("/search")
    public Result<PageBean<Video>> search(String keywords,Integer pageNum,Integer pageSize,
    @RequestParam(required = false) String fromDate,@RequestParam(required = false) String toDate){
        PageBean<Video> pb = videoService.search(keywords,pageNum,pageSize,fromDate,toDate);
        redisTemplate.opsForValue().set("搜索记录"+SnowFlakeUtil.getSnowFlakeId(),pb);
        return Result.success(pb);
    }

}
