package indi.me.computer.frame;

import indi.me.computer.pojo.HardDisk;
import indi.me.computer.service.HardDiskService;
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

public class HardDiskFrame {

  private static volatile HardDiskFrame hardDiskFrame = null;
  private Deque<HardDisk> hardDiskDeque;
  private HardDiskService hardDiskService;

  public static HardDiskFrame getHardDiskFrame() throws InterruptedException, SigarException {
    if (hardDiskFrame == null) {
      synchronized (HardDiskFrame.class) {
        if (hardDiskFrame == null) {
          hardDiskFrame = new HardDiskFrame();
        }
      }
    }
    return hardDiskFrame;
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
  private JLabel label;
  private JLabel lblNewLabel;
  private JLabel label_1;
  private JLabel label_3;
  private ComputerInfo computerInfo;
  private DefaultCategoryDataset categoryDataset;


  {
    try {
      computerInfo = new ComputerInfo();
    } catch (SigarException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the application.
   */
  private HardDiskFrame() throws SigarException, InterruptedException {
    initialize();
    hardDiskDeque = new LinkedList<>();
    hardDiskService = new HardDiskService();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() throws InterruptedException, SigarException {
    frame = new JFrame();
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
    button_2.setEnabled(false);
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
          for (HardDisk hardDisk : hardDiskDeque) {
            hardDiskService.insertOne(hardDisk);
          }
          hardDiskDeque.clear();
        } else {
          while (!hardDiskDeque.isEmpty()) {
            HardDisk student = hardDiskDeque.pop();
            long begin = nowTime - status * 1000;
            if (student.getTime() < nowTime && student.getTime() > begin) {
              hardDiskService.insertOne(student);
            } else {
              hardDiskDeque.push(student);
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
          for (HardDisk cpu : hardDiskDeque) {
            hardDiskService.insertOne(cpu);
          }
          hardDiskDeque.clear();
        } else if (status == 0) {
          if (!hardDiskDeque.isEmpty()) {
            hardDiskService.insertOne(hardDiskDeque.pop());
          }
        } else {
          while (!hardDiskDeque.isEmpty()) {
            HardDisk student = hardDiskDeque.pop();
            long begin = nowTime - status * 1000;
            if (student.getTime() < nowTime && student.getTime() > begin) {
              hardDiskService.insertOne(student);
            } else {
              hardDiskDeque.push(student);
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

    try {
      label = new JLabel("总容量：" + computerInfo.getHardDisk().getTotalAmount());
    } catch (SigarException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    label.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label.setBounds(226, 388, 161, 16);
    frame.getContentPane().add(label);

    lblNewLabel = new JLabel("磁盘使用率：" + computerInfo.getHardDisk().getUsingAmount());
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel.setBounds(226, 413, 141, 16);
    frame.getContentPane().add(lblNewLabel);

    label_1 = new JLabel("写入速度：" + computerInfo.getHardDisk().getWriteSpeed());
    label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label_1.setBounds(379, 413, 206, 16);
    frame.getContentPane().add(label_1);

    label_3 = new JLabel("读入速度：" + computerInfo.getHardDisk().getReadSpeed());
    label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label_3.setBounds(379, 388, 205, 16);
    frame.getContentPane().add(label_3);
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
        paintTable.printExcel(paintTable.getHardDiskTitles());
      }
    });
    btnNewButton_2.setBounds(619, 414, 75, 21);
    frame.getContentPane().add(btnNewButton_2);
  }

  public void refresh(HardDisk hardDisk) {
    if (hardDiskDeque.size() > 50) {
      hardDiskDeque.removeFirst();
    }
    hardDiskDeque.push(hardDisk);
    if (categoryDataset.getColumnCount() > 50) {
      categoryDataset.clear();
    }
    label.setText("总容量：" + hardDisk.getTotalAmount() + "GB");
    lblNewLabel.setText("磁盘使用率：" + hardDisk.getUsingAmount() + "%");
    label_1.setText("写入速度：" + String.format("%.2f", hardDisk.getWriteSpeed()) + "KB/s");
    label_3.setText("读入速度：" + String.format("%.2f", hardDisk.getReadSpeed()) + "KB/s");

    categoryDataset.addValue(hardDisk.getUsingAmount(), "using", hardDisk.getTime());
    categoryDataset.addValue(hardDisk.getReadSpeed(), "read", hardDisk.getTime());
    categoryDataset.addValue(hardDisk.getWriteSpeed(), "write", hardDisk.getTime());

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
