package indi.me.computer.frame;

import indi.me.computer.pojo.Memory;
import indi.me.computer.service.MemoryService;
import indi.me.computer.utils.ComputerInfo;
import indi.me.computer.utils.PaintTable;
import indi.me.computer.utils.Settings;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import lombok.SneakyThrows;
import org.hyperic.sigar.SigarException;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class MemoryFrame {

  private static volatile MemoryFrame memoryFrame = null;
  private MemoryService memoryService;

  private Deque<Memory> memoryDeque;

  public static MemoryFrame getMemoryFrame() throws SigarException {
    if (memoryFrame == null) {
      synchronized (MemoryFrame.class) {
        if (memoryFrame == null) {
          memoryFrame = new MemoryFrame();
        }
      }
    }
    return memoryFrame;
  }

  JFrame frame;
  private JButton button_1;
  private JButton btnNewButton;
  private JButton btnNewButton_1;
  private JButton button_2;
  private JButton button_3;
  private JMenu button_4;
  private JButton button_5;
  private JButton button_6;
  private JLabel lblNewLabel;
  private JLabel label_2;
  private JLabel label;
  private JLabel label_1;
  private ComputerInfo computerInfo = new ComputerInfo();
  private DefaultCategoryDataset categoryDataset;


  /**
   * Create the application.
   */
  private MemoryFrame() throws SigarException {
    initialize();
    memoryDeque = new LinkedList<>();
    memoryService = new MemoryService();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() throws SigarException {
    frame = new JFrame();
    frame.getContentPane().setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    frame.setBounds(100, 100, 700, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JMenuBar menuBar = new JMenuBar();
    menuBar.setToolTipText("os");
    menuBar.setBounds(0, 0, 728, 22);
    frame.getContentPane().add(menuBar);

    button_1 = new JButton("概览");
    button_1.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        StartFrame.getStartFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });
    menuBar.add(button_1);

    btnNewButton = new JButton("cpu");
    menuBar.add(btnNewButton);
    // 给按钮 增加 监听
    btnNewButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        CpuFrame.getCpuFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });

    btnNewButton_1 = new JButton("内存");
    menuBar.add(btnNewButton_1);
    btnNewButton_1.setEnabled(false);
    btnNewButton_1.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        MemoryFrame.getMemoryFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });

    button_2 = new JButton("硬盘");
    menuBar.add(button_2);
    button_2.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        HardDiskFrame.getHardDiskFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });

    button_3 = new JButton("网络");
    button_3.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        NetworkFrame.getNetworkFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });
    menuBar.add(button_3);

    button_4 = new JMenu("历史");
    JMenuItem jMenuItem = new JMenuItem("cpu历史记录");
    JMenuItem jMenuItem1 = new JMenuItem("磁盘历史记录");
    JMenuItem jMenuItem2 = new JMenuItem("网络历史记录");
    JMenuItem jMenuItem3 = new JMenuItem("内存历史记录");

    button_4.add(jMenuItem);
    button_4.add(jMenuItem1);
    button_4.add(jMenuItem2);
    button_4.add(jMenuItem3);

    jMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] titles = {"编号", "cpu核数", "参考速度", "用户速度", "系统速度", "总速度", "记录时间"};
        new PaintTable().paintTable(titles);
      }
    });

    jMenuItem1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] titles = {"编号", "总容量", "使用容量", "读取速度", "写入速度", "记录时间"};
        new PaintTable().paintTable(titles);
      }
    });

    jMenuItem2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] titles = {"编号", "IP地址", "上传速度", "下载速度", "记录时间"};
        new PaintTable().paintTable(titles);
      }
    });
    jMenuItem3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String[] titles = {"编号", "已使用内存", "未使用内存", "总内存", "记录时间"};
        new PaintTable().paintTable(titles);
      }
    });

    button_5 = new JButton("存储");
    button_5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        long nowTime = System.currentTimeMillis();
        int status = Settings.cpuStore;
        if (status == Integer.MAX_VALUE) {
          for (Memory memory : memoryDeque) {
            memoryService.insertOne(memory);
          }
          memoryDeque.clear();
        } else {
          while (!memoryDeque.isEmpty()) {
            Memory student = memoryDeque.pop();
            long begin = nowTime - status * 1000;
            if (student.getTime() < nowTime && student.getTime() > begin) {
              memoryService.insertOne(student);
            } else {
              memoryDeque.push(student);
              break;
            }
          }
        }
      }
    });
    button_5.setBounds(619, 451, 75, 21);
    button_5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (Settings.isLogin == false) {
          JOptionPane.showMessageDialog(frame, "你没有权限，请登陆", "fail", JOptionPane.ERROR_MESSAGE);
          return;
        }
        String inputx = (String) JOptionPane.showInputDialog(frame,

            "请输入你的选择：", "选择",

            JOptionPane.INFORMATION_MESSAGE, null,
            new String[]{"最近一条数据", "5s之内", "30s之内", "所有缓存数据"},
            "");
        Integer status = Settings.map.get(inputx);
        long nowTime = System.currentTimeMillis();
        if (status == Integer.MAX_VALUE) {
          for (Memory cpu : memoryDeque) {
            memoryService.insertOne(cpu);
          }
          memoryDeque.clear();
        } else if (status == 0) {
          if (!memoryDeque.isEmpty()) {
            memoryService.insertOne(memoryDeque.pop());
          }
        } else {
          while (!memoryDeque.isEmpty()) {
            Memory student = memoryDeque.pop();
            long begin = nowTime - status * 1000;
            if (student.getTime() < nowTime && student.getTime() > begin) {
              memoryService.insertOne(student);
            } else {
              memoryDeque.push(student);
              break;
            }
          }
        }
      }
    });
    frame.getContentPane().add(button_5);

    button_6 = new JButton("设置");
    button_6.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SettingsFrame.getSettingsFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });
    menuBar.add(button_6);
    menuBar.add(button_4);
    lblNewLabel = new JLabel("物理内存：" + computerInfo.getTotalMemory());

    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel.setBounds(216, 384, 145, 16);
    frame.getContentPane().add(lblNewLabel);

    label_2 = new JLabel("已使用内存：" + computerInfo.getUsingMemory());

    label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label_2.setBounds(216, 426, 198, 16);
    frame.getContentPane().add(label_2);

    label = new JLabel("可使用内存：" + computerInfo.getFreeMemoey());

    label.setBounds(377, 385, 177, 16);
    frame.getContentPane().add(label);

    label_1 = new JLabel(
        "内存使用率：" + computerInfo.getUsingMemory() * 100 / computerInfo.getTotalMemory());

    label_1.setBounds(377, 427, 161, 16);
    frame.getContentPane().add(label_1);
    categoryDataset = new DefaultCategoryDataset();

    JButton btnNewButton_2 = new JButton("打印数据");
    btnNewButton_2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (Settings.isLogin == false) {
          JOptionPane.showMessageDialog(frame, "你没有权限，请登陆", "fail", JOptionPane.ERROR_MESSAGE);
          return;
        }
        PaintTable paintTable = new PaintTable();
        paintTable.printExcel(paintTable.getMemoryTitles());
      }
    });
    btnNewButton_2.setBounds(619, 414, 75, 21);
    frame.getContentPane().add(btnNewButton_2);
  }

  public void refresh(Memory memory) {
    if (memoryDeque.size() > 50) {
      memoryDeque.removeFirst();
    }
    memoryDeque.push(memory);
    if (categoryDataset.getColumnCount() > 50) {
      categoryDataset.clear();
    }
    lblNewLabel.setText("物理内存：" + memory.getTotalMemory() + "GB");
    label_2.setText("已使用内存：" + String.format("%.2f", memory.getUsingMemory()) + "GB");
    label.setText("可使用内存：" + String.format("%.2f", memory.getUnusingMemory()) + "GB");
    label_1.setText("内存使用率：" + String.format("%.2f", memory.getUsingRate()) + "%");

    categoryDataset.addValue(memory.getUsingMemory(), "using", memory.getTime());
    categoryDataset.addValue(memory.getTotalMemory(), "total", memory.getTime());

    JFreeChart mChart = ChartFactory.createLineChart(
        "",//图名字
        "",//横坐标
        "",//纵坐标
        categoryDataset,//数据集
        PlotOrientation.VERTICAL,
        true, // 显示图例
        true, // 采用标准生成器
        false);// 是否生成超链接
    JPanel panel = new ChartPanel(mChart);
    panel.setBounds(52, 66, 601, 298);
    CategoryPlot plot = mChart.getCategoryPlot();
    plot.setRangeGridlinePaint(ChartColor.green);
    plot.setBackgroundPaint(ChartColor.BLACK);
    frame.getContentPane().add(panel);
    frame.add(panel);

    frame.repaint();
  }

}
