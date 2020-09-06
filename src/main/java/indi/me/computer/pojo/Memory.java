package indi.me.computer.pojo;

import lombok.Data;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 7:14 下午
 */
@Data
public class Memory {

  private Integer id;

  private Double usingMemory;

  private Double unusingMemory;

  private Double totalMemory;

  private Double usingRate;

  private Long time;
}
