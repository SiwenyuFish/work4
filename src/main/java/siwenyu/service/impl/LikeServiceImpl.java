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

    /**
     * 实现给视频点赞的操作 数据保存到like表
     */
    @Override
    public void videoAction(Long id, Long userId, Integer actionType) {
        if(actionType==1){
            likeMapper.actionVideoLike(id,userId);
        }
        else {
            likeMapper.actionVideoDislike(id,userId);
        }
    }

    /**
     * 实现给评论点赞的操作 数据保存到like表
     */

    @Override
    public void commentAction(Long id, Long userId, Integer actionType) {
        if(actionType==1){
            likeMapper.actionCommentLike(id,userId);
        }
        else {
            likeMapper.actionCommentDislike(id,userId);
        }
    }

    /**
     * 分页查询指定用户点赞的视频列表
     */
    @Override
    public MyPageBean<Video> list(Long userId, Integer pageNum, Integer pageSize) {
        MyPageBean<Video> pp =new MyPageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        List<Video> videos=likeMapper.list(userId);
        Page<Video> videoPage=(Page<Video>) videos;

        pp.setItems(videoPage.getResult());

        return pp;
    }

    /**
     * 不分页查询指定用户点赞的视频列表
     */
    @Override
    public MyPageBean<Video> list(Long userId){
        MyPageBean<Video> pp =new MyPageBean<>();
        List<Video> videos=likeMapper.list(userId);
        pp.setItems(videos);
        return pp;
    }
}
