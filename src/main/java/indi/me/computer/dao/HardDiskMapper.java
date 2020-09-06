package indi.me.computer.dao;

import indi.me.computer.pojo.HardDisk;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 6:20 下午
 */

public interface HardDiskMapper {

  @Select("SELECT * FROM harddisk_usage")
  public List<HardDisk> selectAll();

  @Select("SELECT * FROM harddisk_usage WHERE time < #{time} ")
  public List<HardDisk> selectByTime(Long time);

  @Insert("INSERT INTO harddisk_usage(total_amount,using_amount,read_speed,"
      + "write_speed,time) "
      + "VALUES(#{totalAmount},#{usingAmount},#{readSpeed},#{writeSpeed},#{time})")
  public boolean insertOne(HardDisk hardDisk);
}
