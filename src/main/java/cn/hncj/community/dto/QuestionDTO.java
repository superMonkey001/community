package cn.hncj.community.dto;

import cn.hncj.community.bean.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("question")
public class QuestionDTO {
    private  Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;
    private Integer commentCount;
    @TableField(exist = false)
    private User user;
}
