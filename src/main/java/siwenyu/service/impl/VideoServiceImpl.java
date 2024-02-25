package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public void action(String id, Integer actionType) {
        if(actionType==1)
        {
            videoMapper.actionLike(id);
        }else {
            videoMapper.actionDislike(id);
        }
    }

    @Override
    public Video searchById(String id) {
        return videoMapper.searchById(id);
    }
}
