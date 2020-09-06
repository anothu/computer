package indi.me.computer.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/11 4:11 下午
 */
public interface LoginMapper {

  @Select("SELECT count(*) FROM user WHERE name = #{user} and password= #{password}")
  public Integer login(@Param("user") String user, @Param("password") String password);
}
