package indi.me.computer.utils;

import indi.me.computer.pojo.Cpu;
import indi.me.computer.pojo.HardDisk;
import indi.me.computer.pojo.Memory;
import indi.me.computer.pojo.Network;
import indi.me.computer.service.CpuService;
import indi.me.computer.service.HardDiskService;
import indi.me.computer.service.MemoryService;
import indi.me.computer.service.NetworkService;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/11 9:44 上午
 */
public class PaintTable {

  private String dateString(long l) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(l);
    return df.format(date);
  }

  CpuService cpuService = new CpuService();

  public String[] getCpuTitles() {
    return cpuTitles;
  }

  private final String[] cpuTitles = {"编号", "cpu核数", "参考速度", "用户速度", "系统速度", "总速度", "记录时间"};

  private String[][] cpuTable() {
    List<Cpu> cpus = cpuService.selectAll();
    String[][] result = new String[cpus.size()][cpuTitles.length];
    int i = 0;
    for (Cpu cpu : cpus) {
      result[i][0] = cpu.getId() + "";
      result[i][1] = cpu.getCpuNumber() + "";
      result[i][2] = cpu.getReferenceSpeed() + "";
      result[i][3] = cpu.getUserUtilizationRate() + "";
      result[i][4] = cpu.getSystemUtilizationRate() + "";
      result[i][5] = cpu.getTotalUtilizationRate() + "";
      result[i][6] = dateString(cpu.getTime());
      i++;
    }
    return result;
  }

  HardDiskService hardDiskService = new HardDiskService();
  private final String[] hardDiskTitles = {"编号", "总容量", "使用容量", "读取速度", "写入速度", "记录时间"};

  private String[][] hardDiskTable() {
    List<HardDisk> hardDisks = hardDiskService.selectAll();
    String[][] result = new String[hardDisks.size()][hardDiskTitles.length];

    int i = 0;
    for (HardDisk hardDisk : hardDisks) {
      result[i][0] = hardDisk.getId() + "";
      result[i][1] = hardDisk.getTotalAmount() + "";
      result[i][2] = hardDisk.getUsingAmount() + "";
      result[i][3] = hardDisk.getReadSpeed() + "";
      result[i][4] = hardDisk.getWriteSpeed() + "";
      result[i][5] = dateString(hardDisk.getTime());
      i++;
    }
    return result;
  }

  MemoryService memoryService = new MemoryService();
  private final String[] memoryTitles = {"编号", "已使用内存", "未使用内存", "总内存", "记录时间"};

  private String[][] memoryTable() {
    List<Memory> memories = memoryService.selectAll();
    String[][] result = new String[memories.size()][memoryTitles.length];

    int i = 0;
    for (Memory memory : memories) {
      result[i][0] = memory.getId() + "";
      result[i][1] = memory.getUsingMemory() + "";
      result[i][2] = memory.getUnusingMemory() + "";
      result[i][3] = memory.getTotalMemory() + "";
      result[i][4] = dateString(memory.getTime());
      i++;
    }
    return result;
  }

  NetworkService networkService = new NetworkService();

  public String[] getHardDiskTitles() {
    return hardDiskTitles;
  }

  public String[] getMemoryTitles() {
    return memoryTitles;
  }

  public String[] getNetworkTitles() {
    return networkTitles;
  }

  private final String[] networkTitles = {"编号", "IP地址", "上传速度", "下载速度", "记录时间"};


  private String[][] networkTable() {
    List<Network> networks = networkService.selectAll();
    String[][] result = new String[networks.size()][networkTitles.length];

    int i = 0;
    for (Network network : networks) {
      result[i][0] = network.getId() + "";
      result[i][1] = network.getIp() + "";
      result[i][2] = network.getUploadSpeed() + "";
      result[i][3] = network.getDownloadSpeed() + "";
      result[i][4] = dateString(network.getTime());
      i++;
    }
    return result;
  }

  public void paintTable(String[] titles) {
    String[][] datas = {};
    switch (titles[1]) {
      case "cpu核数": {
        datas = cpuTable();
        break;
      }
      case "总容量": {
        datas = hardDiskTable();
        break;
      }
      case "IP地址": {
        datas = networkTable();
        break;
      }
      case "已使用内存": {
        datas = memoryTable();
        break;
      }
      default:
        datas = null;
    }
    JTable table = new JTable(datas, titles);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(100, 100, 700, 500);
    JFrame f = new JFrame();
    f.setBounds(100, 100, 700, 500);
    f.getContentPane().add(scrollPane);
    f.setTitle("历史记录");
    f.pack();
    f.show();
    f.setVisible(true);
  }

  public void printExcel(String[] titles) {
    String[][] datas = null;
    switch (titles[1]) {
      case "cpu核数": {

        datas = cpuTable();
        break;
      }
      case "总容量": {
        datas = hardDiskTable();
        break;
      }
      case "IP地址": {
        datas = networkTable();
        break;
      }

      case "已使用内存": {
        datas = memoryTable();
        break;
      }
      default:
        datas = null;
    }

    HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(UUID.randomUUID().toString(), titles, datas);
    //创建xls文件，无内容 0字节
    FileOutputStream fOut = null;
    try {
      fOut = new FileOutputStream(Settings.location + "/" + UUID.randomUUID().toString() + ".xlsx");
      //写内容，xls文件已经可以打开
      wb.write(fOut);
      //刷新缓冲区
      fOut.flush();
      //关闭
      fOut.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
