package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.LikeMapper;
import siwenyu.pojo.MyPageBean;
import siwenyu.pojo.Video;
import siwenyu.service.LikeService;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;


    @Override
    public void videoAction(String id, Long userId, Integer actionType) {
        if(actionType==1){
            likeMapper.actionVideoLike(id,userId);
        }
        else {
            likeMapper.actionVideoDislike(id,userId);
        }
    }

    @Override
    public void commentAction(String id, Long userId, Integer actionType) {
        if(actionType==1){
            likeMapper.actionCommentLike(id,userId);
        }
        else {
            likeMapper.actionCommentDislike(id,userId);
        }
    }

    @Override
    public MyPageBean<Video> list(Long userId, Integer pageNum, Integer pageSize) {
        MyPageBean<Video> pp =new MyPageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        List<Video> videos=likeMapper.list(userId);
        Page<Video> videoPage=(Page<Video>) videos;

        pp.setItems(videoPage.getResult());

        return pp;
    }
    @Override
    public MyPageBean<Video> list(Long userId){
        MyPageBean<Video> pp =new MyPageBean<>();
        List<Video> videos=likeMapper.list(userId);
        pp.setItems(videos);
        return pp;
    }
}
