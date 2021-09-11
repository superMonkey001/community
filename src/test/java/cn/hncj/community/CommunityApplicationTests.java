package cn.hncj.community;

import cn.hncj.community.bean.Question;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.mapper.QuestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {
    @Autowired
    QuestionDTOMapper mapper;
    @Test
    void contextLoads() {
        QuestionDTO question = mapper.selectById(1);
        System.out.println(question);
    }

}
