package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.CommentMapper;
import siwenyu.mapper.VideoMapper;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.Video;
import siwenyu.service.VideoService;
import siwenyu.utils.SnowFlakeUtil;
import siwenyu.utils.ThreadLocalUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 实现视频上传
     */
    @Override
    public void upload(String url, String title, String describe) {
        HashMap<String, Object> map=new HashMap<String,Object>();
        map.put("id", SnowFlakeUtil.getSnowFlakeId());

        Map<String,Object> map2 = ThreadLocalUtil.get();
        Long id = (Long) map2.get("id");

        map.put("user_id",id);
        map.put("video_url",url);
        String coverUrl=url.substring(0,url.lastIndexOf("."))+".jpeg";
        map.put("cover_url",coverUrl);
        map.put("title",title);
        map.put("description",describe);

        videoMapper.upload(map);
    }

    /**
     * 实现分页查询视频列表
     */
    @Override
    public PageBean<Video> list(Long userId, Integer pageNum, Integer pageSize) {

        PageBean<Video> pb =new PageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        List<Video> videos=videoMapper.list(userId);
        Page<Video>videoPage=(Page<Video>) videos;

        pb.setTotal(videoPage.getTotal());
        pb.setItems(videoPage.getResult());

        return pb;
    }

    /**
     * 实现关键字搜索视频列表
     */
    @Override
    public PageBean<Video> search(String keywords, Integer pageNum, Integer pageSize, String fromDate, String toDate) {
        PageBean<Video> pb =new PageBean<>();

        PageHelper.startPage(pageNum,pageSize);


        List<Video> videos=videoMapper.search(keywords,fromDate,toDate);

        Page<Video>videoPage=(Page<Video>) videos;

        pb.setTotal(videoPage.getTotal());
        pb.setItems(videoPage.getResult());

        return pb;
    }

    /**
     * 给视频点赞
     */
    @Override
    public void action(Long id, Integer actionType) {
        if(actionType==1)
        {
            videoMapper.actionLike(id);
        }else {
            videoMapper.actionDislike(id);
        }
    }

    /**
     * 给视频取消点赞
     */
    @Override
    public Video searchById(Long id) {
        return videoMapper.searchById(id);
    }

    @Override
    public void publishByVideoId(Long videoId) {
        videoMapper.publishByVideoId(videoId);
    }

    @Override
    public Long publishByCommentId(Long commentId) {
        Long videoId = commentMapper.findByVideoId(commentId);
        publishByVideoId(videoId);
        return videoId;
    }

    @Override
    public void deleteComment(int count, Long videoId) {
        videoMapper.deleteComment(count,videoId);
    }

    @Override
    public Long searchByCommentId(Long commentId) {
        Long videoId = commentMapper.findByVideoId(commentId);
        return videoId;
    }
}
