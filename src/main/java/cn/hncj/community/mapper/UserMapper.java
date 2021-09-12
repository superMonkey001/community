package cn.hncj.community.mapper;

import cn.hncj.community.bean.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User selectByToken(String token);
    @Select("select * from user where id = #{id}")
    User findById(Integer id);
    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);
    @Update("update user  set token=#{token},name=#{name},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User dbUser);
}
