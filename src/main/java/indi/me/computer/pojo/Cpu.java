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
public class Cpu {

  private Integer id;
  private Integer cpuNumber;
  private Double referenceSpeed;
  private Double userUtilizationRate;
  private Double systemUtilizationRate;
  private Double totalUtilizationRate;
  private Long time;
}
