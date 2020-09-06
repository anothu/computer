package indi.me.computer.dao;

import indi.me.computer.pojo.Cpu;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 4:28 下午
 */

public interface CpuMapper {

  @Select("SELECT * FROM cpu_usage")
  public List<Cpu> selectAll();

  @Select("SELECT * FROM cpu_usage WHERE time < #{time} ")
  public List<Cpu> selectByTime(Long time);

  @Insert("INSERT INTO cpu_usage(cpu_number,reference_speed,user_utilization_rate,"
      + "system_utilization_rate,total_utilization_rate,time) "
      + "VALUES(#{cpuNumber},#{referenceSpeed},#{userUtilizationRate},#{systemUtilizationRate},#{totalUtilizationRate},#{time})")
  public boolean insertOne(Cpu cpu);
}
