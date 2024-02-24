package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.CommentMapper;
import siwenyu.pojo.Comment;
import siwenyu.pojo.PagePojo;
import siwenyu.service.CommentService;
import siwenyu.utils.SnowFlakeUtil;
import siwenyu.utils.ThreadLocalUtil;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void action(String id, Integer actionType) {
        if(actionType==1)
        {
            commentMapper.actionLike(id);
        }else {
            commentMapper.actionDislike(id);
        }
    }

    @Override
    public void publish(Long videoId, Long commentId, String content) {
        Long id = SnowFlakeUtil.getSnowFlakeId();
        Map<String,Object> map = ThreadLocalUtil.get();
        Long userId = (Long) map.get("id");
        commentMapper.publish(id,userId,videoId,commentId,content);
    }

    @Override
    public PagePojo<Comment> list(Long videoId, Long commentId, Integer pageNum, Integer pageSize) {
        PagePojo<Comment> pp =new PagePojo<>();

        PageHelper.startPage(pageNum,pageSize);

        List<Comment> comments=commentMapper.list(videoId,commentId);
        Page<Comment> commentPage=(Page<Comment>) comments;

        pp.setItems(commentPage.getResult());

        return pp;
    }

    @Override
    public PagePojo<Comment> list(Long videoId, Long commentId) {
        PagePojo<Comment> pp =new PagePojo<>();
        List<Comment> comments=commentMapper.list(videoId,commentId);
        pp.setItems(comments);
        return pp;
    }

    @Override
    public void delete(Long videoId, Long commentId) {
        commentMapper.delete(videoId,commentId);
    }
}
