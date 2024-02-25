package siwenyu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siwenyu.mapper.RelationMapper;
import siwenyu.pojo.Friend;
import siwenyu.pojo.PageBean;
import siwenyu.pojo.MyPageBean;
import siwenyu.service.RelationService;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Override
    public void action( Long id,Long userId, Integer actionType) {
        if(actionType==0){
            relationMapper.actionFollow(id,userId);
        }else {
            relationMapper.actionUnfollow(id,userId);
        }
    }

    @Override
    public PageBean<Friend> list(Long userId, Integer pageNum, Integer pageSize) {
        PageBean<Friend> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Friend> friends = relationMapper.list(userId);

        Page<Friend> friendPage = (Page<Friend>) friends;

        pb.setItems(friendPage.getResult());
        pb.setTotal(friendPage.getTotal());

        return pb;
    }

    @Override
    public MyPageBean<Friend> list(Long userId) {
        MyPageBean<Friend> pp=new MyPageBean<>();
        List<Friend>friends=relationMapper.list(userId);

        pp.setItems(friends);
        return pp;
    }

    @Override
    public PageBean<Friend> fanslist(Long userId, Integer pageNum, Integer pageSize) {
        PageBean<Friend> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Friend> friends = relationMapper.fanslist(userId);

        Page<Friend> friendPage = (Page<Friend>) friends;

        pb.setItems(friendPage.getResult());
        pb.setTotal(friendPage.getTotal());

        return pb;
    }

    @Override
    public MyPageBean<Friend> fanslist(Long userId) {
        MyPageBean<Friend> pp=new MyPageBean<>();
        List<Friend>friends=relationMapper.fanslist(userId);

        pp.setItems(friends);
        return pp;
    }

    @Override
    public PageBean<Friend> friendslist(Long userId, Integer pageNum, Integer pageSize) {
        PageBean<Friend> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);


        List<Friend> friends = relationMapper.friendslist(userId);

        Page<Friend> friendPage = (Page<Friend>) friends;

        pb.setItems(friendPage.getResult());
        pb.setTotal(friendPage.getTotal());

        return pb;
    }

    @Override
    public MyPageBean<Friend> friendslist(Long userId) {
        MyPageBean<Friend> pp=new MyPageBean<>();
        List<Friend>friends=relationMapper.friendslist(userId);

        pp.setItems(friends);
        return pp;
    }
}
