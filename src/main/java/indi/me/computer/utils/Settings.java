package indi.me.computer.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 7:16 下午
 */

public class Settings {

  public static Map<String, Integer> map = new HashMap<>();

  static {
    map.put("最近一条数据", 0);
    map.put("5s之内", 5);
    map.put("30s之内", 30);
    map.put("1分钟之内", 60);
    map.put("所有缓存数据", Integer.MAX_VALUE);
    map.put("1s一次", 1);
    map.put("3s一次", 3);
    map.put("5s一次", 5);
  }

  public static volatile int cpuRefresh = 5;
  public static volatile int HardRefresh = 5;
  public static volatile int MemeoryRefresh = 5;
  public static volatile int NetworkRefresh = 5;
  public static volatile int cpuStore = 5;
  public static volatile boolean isLogin = false;
  public static volatile String location = "/Users/yulinxiao/desktop";
}
