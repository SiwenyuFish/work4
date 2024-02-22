package siwenyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.Result;
import siwenyu.pojo.Video;
import siwenyu.service.VideoService;
import siwenyu.utils.AliOssUtil;
import siwenyu.utils.SnowFlakeUtil;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

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

    @PostMapping("/search")
    public Result<PageBean<Video>> search(String keywords,Integer pageNum,Integer pageSize,
    @RequestParam(required = false) String fromDate,@RequestParam(required = false) String toDate){
        PageBean<Video> pb = videoService.search(keywords,pageNum,pageSize,fromDate,toDate);
        return Result.success(pb);
    }

}
