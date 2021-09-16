package cn.hncj.community;

import cn.hncj.community.bean.Question;
import cn.hncj.community.dto.QuestionDTO;
import cn.hncj.community.mapper.QuestionDTOMapper;
import cn.hncj.community.mapper.QuestionMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CommunityApplicationTests {
    @Autowired
    QuestionDTOMapper mapper;
    @Test
    void contextLoads() {
        QuestionDTO question = mapper.selectById(1);
        System.out.println(question);
    }
    @Test
    void mbg() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
