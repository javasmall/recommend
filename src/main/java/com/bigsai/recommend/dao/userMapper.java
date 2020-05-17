package com.bigsai.recommend.dao;

import com.bigsai.recommend.pojo.users;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface userMapper {

    @Select("select pre_list from users where username=#{username}")
    String getUserpre(String username);

    @Update("update users set pre_list=#{preferString} where username=#{username}")
    boolean updatePreListbyid(@Param("preferString") String userPreferString, @Param("username") String username);

    @Select("select * from users where  TO_DAYS( NOW( ) ) - TO_DAYS(last_login) <= 1 ")
    List<users> getLastDayUsers();

    @Insert("insert into users(username,pre_list,last_login)values(#{username},#{preList},#{lastLogin})")
    boolean insertUsers(users user);

    @Update("update users set last_login=#{lastLogin} where username=#{username}")
    boolean updateLastLoginTime(users users);

    boolean updatePreList(@Param("list")List<users> usersList);
}
