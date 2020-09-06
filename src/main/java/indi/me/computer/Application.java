package indi.me.computer;

import indi.me.computer.frame.CpuFrame;
import indi.me.computer.frame.HardDiskFrame;
import indi.me.computer.frame.MemoryFrame;
import indi.me.computer.frame.NetworkFrame;
import indi.me.computer.frame.SettingsFrame;
import indi.me.computer.frame.StartFrame;
import indi.me.computer.pojo.Cpu;
import indi.me.computer.pojo.HardDisk;
import indi.me.computer.pojo.Memory;
import indi.me.computer.pojo.Network;
import indi.me.computer.utils.ComputerInfo;
import indi.me.computer.utils.Settings;
import lombok.SneakyThrows;
import org.hyperic.sigar.SigarException;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/4 11:51 上午
 */
public class Application {


  static {
    CpuFrame.getCpuFrame();
    try {
      HardDiskFrame.getHardDiskFrame();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (SigarException e) {
      e.printStackTrace();
    }
//    HistoryFrame.getHistoryFrame();
    try {
      MemoryFrame.getMemoryFrame();
    } catch (SigarException e) {
      e.printStackTrace();
    }
    try {
      NetworkFrame.getNetworkFrame();
    } catch (Exception e) {
      e.printStackTrace();
    }
    SettingsFrame.getSettingsFrame();
    StartFrame.getStartFrame();
//    StoreFrame.getStoreFrame();

  }

  public static void main(String[] args) throws SigarException {
    StartFrame.getStartFrame().frame.setVisible(true);
    Thread cpuThread = new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        while (true) {
          Cpu cpu = new Cpu();
          ComputerInfo computerInfo = new ComputerInfo();
          cpu.setReferenceSpeed(computerInfo.getCpuBasicSpeed() / 1000);
          cpu.setUserUtilizationRate(computerInfo.getCpuUserSpeed() * 100);
          cpu.setSystemUtilizationRate(computerInfo.getCpuSysSpeed() * 100);
          cpu.setTotalUtilizationRate(computerInfo.getCpuCombineSpeed() * 100);
          long now = System.currentTimeMillis();
          cpu.setTime(now);
          cpu.setCpuNumber(8);
          CpuFrame.getCpuFrame().refresh(cpu);
          try {
            Thread.sleep(Settings.cpuRefresh * 1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    Thread hardDiskThread = new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        while (true) {
          ComputerInfo computerInfo = new ComputerInfo();
          HardDisk hardDisk = computerInfo.getHardDisk();
          long now = System.currentTimeMillis();
          hardDisk.setTime(now);
          HardDiskFrame.getHardDiskFrame().refresh(hardDisk);
          Thread.sleep(Settings.HardRefresh * 1000);
        }
      }
    });
    Thread memoryThread = new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        while (true) {
          Memory memory = new Memory();
          ComputerInfo computerInfo = new ComputerInfo();
          memory.setUsingMemory(computerInfo.getUsingMemory() - 1.68);
          memory.setTotalMemory(computerInfo.getTotalMemory());
          memory.setUnusingMemory(
              computerInfo.getTotalMemory() - (computerInfo.getUsingMemory() - 1.68));
          memory.setUsingRate(
              (computerInfo.getUsingMemory() - 1.68) * 100 / computerInfo.getTotalMemory());
          long now = System.currentTimeMillis();
          memory.setTime(now);
          MemoryFrame.getMemoryFrame().refresh(memory);

          Thread.sleep(Settings.MemeoryRefresh * 1000);
        }
      }
    });
    Thread networkThread = new Thread(new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        while (true) {
          Network network = new Network();
          ComputerInfo computerInfo = new ComputerInfo();
          double[] temp = computerInfo.getNetSpeed();
          network.setIp(computerInfo.getIp());
          network.setUploadSpeed(temp[0]);
          network.setDownloadSpeed(temp[1]);
          long now = System.currentTimeMillis();
          network.setTime(now);
          NetworkFrame.getNetworkFrame().refresh(network);
          Thread.sleep(Settings.NetworkRefresh * 1000);
        }
      }
    });
    cpuThread.start();
    hardDiskThread.start();
    memoryThread.start();
    networkThread.start();
  }

}
