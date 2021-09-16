package cn.hncj.community.dto;

import cn.hncj.community.bean.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String type;

    public void setTypeName(String nameOfType) {

    }
}
