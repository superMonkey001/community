package cn.hncj.community.service;

import cn.hncj.community.bean.Question;
import cn.hncj.community.dto.QuestionDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface QuestionDTOService extends IService<QuestionDTO> {
    List<QuestionDTO> list();

    void intView(Integer id);
}
