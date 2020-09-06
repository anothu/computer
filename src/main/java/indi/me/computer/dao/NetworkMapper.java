package indi.me.computer.dao;

import indi.me.computer.pojo.Network;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 6:29 下午
 */

public interface NetworkMapper {

  @Select("SELECT * FROM network_speed")
  public List<Network> selectAll();

  @Select("SELECT * FROM network_speed WHERE time < #{time} ")
  public List<Network> selectByTime(Long time);

  @Insert("INSERT INTO network_speed(ip,upload_speed,download_speed,"
      + "time) "
      + "VALUES(#{ip},#{uploadSpeed},#{downloadSpeed},#{time})")
  public boolean insertOne(Network network);
}
