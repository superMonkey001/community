package cn.hncj.community.service;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.dto.CommentDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService extends IService<Comment> {
    @Transactional
    void insert(Comment comment);

    List<CommentDTO> listByQuestionId(Integer id);
}
