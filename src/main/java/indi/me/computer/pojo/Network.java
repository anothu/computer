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
public class Network {

  private Integer id;

  private String ip;

  private Double uploadSpeed;

  private Double downloadSpeed;

  private Long time;

}
