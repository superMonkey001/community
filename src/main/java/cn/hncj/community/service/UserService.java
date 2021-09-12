package cn.hncj.community.service;

import cn.hncj.community.bean.User;
import cn.hncj.community.mapper.QuestionMapper;
import cn.hncj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    void createOrUpdate(User user);
}
