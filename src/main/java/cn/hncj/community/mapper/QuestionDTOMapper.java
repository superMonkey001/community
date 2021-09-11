package cn.hncj.community.mapper;

import cn.hncj.community.bean.Question;
import cn.hncj.community.dto.QuestionDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface QuestionDTOMapper extends BaseMapper<QuestionDTO> {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag,creator) values" +
            " (#{title},#{description},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    void create(Question question);
    @Select("select * from question")
    List<Question> list();
}
