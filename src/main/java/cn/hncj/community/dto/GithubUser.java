package cn.hncj.community.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String dio;
    private String avatar_url;
}
