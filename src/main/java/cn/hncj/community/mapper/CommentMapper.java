package cn.hncj.community.mapper;

import cn.hncj.community.bean.Comment;
import cn.hncj.community.dto.CommentDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
