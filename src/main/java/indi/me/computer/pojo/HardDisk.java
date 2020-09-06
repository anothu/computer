package indi.me.computer.pojo;

import lombok.Data;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 11:16 上午
 */

@Data
public class HardDisk {

  private Integer id;

  private Double totalAmount;

  private Double usingAmount;

  private Double readSpeed;

  private Double writeSpeed;

  private Long time;
}
