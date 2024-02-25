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


@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, @RequestParam(required = false) String title, @RequestParam(required = false) String description) throws Exception {

        String originalFilename = file.getOriginalFilename();

        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = SnowFlakeUtil.getSnowFlakeId() + originalFilename.substring(originalFilename.lastIndexOf("."));

        String url = AliOssUtil.uploadFile(filename, file.getInputStream());

        videoService.upload(url, title, description);

        return Result.success(url);
    }

    @GetMapping("/list")
    public Result<PageBean<Video>> list(Long userId, Integer pageNum, Integer pageSize) {
        PageBean<Video> pb = videoService.list(userId, pageNum, pageSize);
        return Result.success(pb);
    }

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

    @PostMapping("/search")
    public Result<PageBean<Video>> search(String keywords,Integer pageNum,Integer pageSize,
    @RequestParam(required = false) String fromDate,@RequestParam(required = false) String toDate){
        PageBean<Video> pb = videoService.search(keywords,pageNum,pageSize,fromDate,toDate);
        redisTemplate.opsForValue().set("搜索记录"+SnowFlakeUtil.getSnowFlakeId(),pb);
        return Result.success(pb);
    }

}
