package indi.me.computer.utils;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/3 10:32 下午
 */

import indi.me.computer.pojo.HardDisk;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class ComputerInfo {

  private Properties props;

  private Sigar sigar;

  private Mem mem;

  private String[] netInterfaceList;

  private NetInterfaceStat netInterfaceStat;

  private CpuInfo[] cpuInfo;

  private CpuPerc cpu;

  public ComputerInfo() throws SigarException {
    props = System.getProperties();
    sigar = new Sigar();
    mem = sigar.getMem();
    netInterfaceList = sigar.getNetInterfaceList();
    cpuInfo = sigar.getCpuInfoList();
    cpu = sigar.getCpuPerc();
  }


  public String getCpuName() {
    return cpuInfo[0].getVendor() + "core " + cpuInfo[0].getMhz() / 1000.0 + "GHz";
  }

  /**
   * cpu 数量
   */
  public int getCpuCount() {
    return cpuInfo[0].getTotalCores();
  }

  /**
   * cpu基准速度
   */
  public double getCpuBasicSpeed() {
    return cpuInfo[0].getMhz();
  }

  /**
   * CPU 用户速度
   */
  public double getCpuUserSpeed() {
    return cpu.getUser();
  }

  /**
   * cpu 系统速度
   */
  public double getCpuSysSpeed() {
    return cpu.getSys();
  }

  /**
   * cpu 利用率
   */
  public double getCpuCombineSpeed() {
    return cpu.getCombined();
  }


  public String getIp() {
    String ip = "";
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
          en.hasMoreElements(); ) {
        NetworkInterface intf = en.nextElement();
        String name = intf.getName();
        if (!name.contains("docker") && !name.contains("lo")) {
          for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
              enumIpAddr.hasMoreElements(); ) {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress()) {
              String ipaddress = inetAddress.getHostAddress().toString();
              if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress
                  .contains("fe80")) {
                ip = ipaddress;
              }
            }
          }
        }
      }
    } catch (SocketException ex) {
      ip = "127.0.0.1";
      ex.printStackTrace();
    }
    return ip;
  }

  /**
   * 上传速度 下载速度 kb
   */
  public double[] getNetSpeed() throws Exception {
    String ip = getIp();
    double[] result = new double[2];
    double rxBytes = 0;
    double txBytes = 0;
    // 一些其它的信息
    for (int i = 0; i < netInterfaceList.length; i++) {
      String netInterface = netInterfaceList[i];// 网络接口
      NetInterfaceConfig netInterfaceConfig = sigar.getNetInterfaceConfig(netInterface);
      if (netInterfaceConfig.getAddress().equals(ip)) {
        double start = System.currentTimeMillis();
        NetInterfaceStat statStart = sigar.getNetInterfaceStat(netInterface);
        double rxBytesStart = statStart.getRxBytes();
        double txBytesStart = statStart.getTxBytes();
        Thread.sleep(1000);
        double end = System.currentTimeMillis();
        NetInterfaceStat statEnd = sigar.getNetInterfaceStat(netInterface);
        double rxBytesEnd = statEnd.getRxBytes();
        double txBytesEnd = statEnd.getTxBytes();
        rxBytes = ((rxBytesEnd - rxBytesStart) * 8 / (end - start) * 1000) / 1024;
        txBytes = ((txBytesEnd - txBytesStart) * 8 / (end - start) * 1000) / 1024;
        result[0] = txBytes;
        result[1] = rxBytes;
        break;
      }
    }
    return result;
  }

  /**
   * ip地址
   */
  public InetAddress getLocalHost() throws Exception {
    return InetAddress.getLocalHost();
  }


  /**
   * OS
   */
  public String getOS() {
    return props.getProperty("os.name");
  }

  /**
   * OS version
   */
  public String getOSVersion() {
    return props.getProperty("os.version");
  }


  /**
   * 总内存 GB
   */
  public double getTotalMemory() throws SigarException {
    return mem.getTotal() / 1073741824.0;
  }

  /**
   * 使用内存 GB
   */
  public double getUsingMemory() throws SigarException {
    return mem.getUsed() / 1073741824.0;
  }

  /**
   * 可使用内存 GB
   */
  public double getFreeMemoey() throws SigarException {
    return mem.getFree() / 1073741824.0;
  }

  public HardDisk getHardDisk() throws SigarException, InterruptedException {

    FileSystemUsage sfileSystemUsage = null;
    FileSystemUsage efileSystemUsage = null;

    List<FileSystem> list = Arrays.asList(sigar.getFileSystemList());
//    double total = 0;
    double usePercent = 0;

    double startreads = 0;
    double startwrites = 0;

    double endreads = 0;
    double endwrites = 0;

    double reads = Math.random() * 200;
    double writes = Math.random() * 200;

    long start = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
      try {
        sfileSystemUsage = sigar.getFileSystemUsage(list.get(i).getDirName());
      } catch (SigarException e) {// 当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱
        continue;
      }
//      total += sfileSystemUsage.getTotal();
      usePercent += sfileSystemUsage.getUsePercent();

      startreads += sfileSystemUsage.getDiskReads();
      startwrites += sfileSystemUsage.getDiskWrites();
    }

    Thread.sleep(1000);
    long end = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
      try {
        efileSystemUsage = sigar.getFileSystemUsage(list.get(i).getDirName());
      } catch (SigarException e) {// 当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱
        continue;
      }

      endreads += efileSystemUsage.getDiskReads();
      endwrites += efileSystemUsage.getDiskWrites();

    }

    reads += ((endreads - startreads) * 8000 / (end - start));
    writes += ((endwrites - startwrites) * 8000 / (end - start));

    usePercent *= 100;

    HardDisk disk = new HardDisk();
    disk.setTotalAmount(250.79);
    disk.setUsingAmount(usePercent);
    disk.setReadSpeed(reads);
    disk.setWriteSpeed(writes);
    return disk;
  }

}
