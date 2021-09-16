package cn.hncj.community.service;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.bean.User;
import cn.hncj.community.dto.CommentDTO;
import cn.hncj.community.enums.CommentTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService extends IService<Comment> {
    @Transactional
    void insert(Comment comment, User user);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);
}
