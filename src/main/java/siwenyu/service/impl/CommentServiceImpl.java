package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.CommentMapper;
import siwenyu.pojo.Comment;
import siwenyu.pojo.MyPageBean;
import siwenyu.service.CommentService;
import siwenyu.utils.SnowFlakeUtil;
import siwenyu.utils.ThreadLocalUtil;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 实现给评论点赞的操作 数据更新到comment表
     */
    @Override
    public void action(String id, Integer actionType) {
        if(actionType==1)
        {
            commentMapper.actionLike(id);
        }else {
            commentMapper.actionDislike(id);
        }
    }

    /**
     * 实现发表评论的操作 数据更新到comment表
     */

    @Override
    public void publish(Long videoId, Long commentId, String content) {
        Long id = SnowFlakeUtil.getSnowFlakeId();
        Map<String,Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        commentMapper.publish(id,userId,videoId,commentId,content);
    }

    /**
     * 分页查询指定视频下的评论或者指定评论下的子评论
     */
    @Override
    public MyPageBean<Comment> list(Long videoId, Long commentId, Integer pageNum, Integer pageSize) {
        MyPageBean<Comment> pp =new MyPageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        List<Comment> comments=commentMapper.list(videoId,commentId);
        Page<Comment> commentPage=(Page<Comment>) comments;

        pp.setItems(commentPage.getResult());

        return pp;
    }

    /**
     * 不分页查询指定视频下的评论或者指定评论下的子评论
     */
    @Override
    public MyPageBean<Comment> list(Long videoId, Long commentId) {
        MyPageBean<Comment> pp =new MyPageBean<>();
        List<Comment> comments=commentMapper.list(videoId,commentId);
        pp.setItems(comments);
        return pp;
    }

    /**
     * 删除指定视频id或者评论id下的评论
     */
    @Override
    public void delete(Long videoId, Long commentId) {
        commentMapper.delete(videoId,commentId);
    }
}
