package cn.hncj.community.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@TableName("question")
public class Question {
    //id  title   description  gmt_create  gmt_modified  view_count  like_count  tag
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
}
