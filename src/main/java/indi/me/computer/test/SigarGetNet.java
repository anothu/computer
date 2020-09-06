package indi.me.computer.test;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:38 上午
 * <p>
 * TODO Description
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 * <p>
 * TODO Description
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 * <p>
 * TODO Description
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 * <p>
 * TODO Description
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 * <p>
 * TODO Description
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 */
/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 9:37 上午
 */

import indi.me.computer.pojo.Cpu;
import indi.me.computer.service.CpuService;
import java.util.List;
import org.hyperic.sigar.SigarException;

public class SigarGetNet {

  public static void main(String[] args) throws SigarException, InterruptedException {
    CpuService cpuService = new CpuService();
    List<Cpu> cpus = cpuService.selectByTime(1596899136L);
    System.out.println(cpus.toArray());
  }
}

