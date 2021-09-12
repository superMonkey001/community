package cn.hncj.community.service.impl;

import cn.hncj.community.bean.User;
import cn.hncj.community.mapper.UserMapper;
import cn.hncj.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null )
        {user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else{//更新用户
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setName(user.getName());
            userMapper.update(dbUser);
        }
    }
}
