package indi.me.computer.dao;

import indi.me.computer.pojo.Memory;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 6:25 下午
 */
//private Integer id;
//
//private Double usingMemory;
//
//private Double unusingMemory;
//
//private Double totalMemory;
//
//private Double usingRate;
//
//private Long time;
public interface MemoryMapper {

  @Select("SELECT * FROM memory_usage")
  public List<Memory> selectAll();

  @Select("SELECT * FROM memory_usage WHERE time < #{time} ")
  public List<Memory> selectByTime(Long time);

  @Insert("INSERT INTO memory_usage(using_memory,total_memory,unusing_memory,time) "
      + "VALUES(#{usingMemory},#{totalMemory},#{unusingMemory},#{time})")
  public boolean insertOne(Memory memory);
}
