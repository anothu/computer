package indi.me.computer.frame;

import indi.me.computer.pojo.Network;
import indi.me.computer.service.NetworkService;
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
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class NetworkFrame {

  private static volatile NetworkFrame networkFrame = null;
  private Deque<Network> networkStack;
  private NetworkService networkService;

  public static NetworkFrame getNetworkFrame() throws Exception {
    if (networkFrame == null) {
      synchronized (NetworkFrame.class) {
        if (networkFrame == null) {
          networkFrame = new NetworkFrame();
        }
      }
    }
    return networkFrame;
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
  private JLabel lblIp;
  private JLabel lblNewLabel;
  private JLabel label_3;
  private ComputerInfo computerInfo = new ComputerInfo();
  private DefaultCategoryDataset categoryDataset;

  /**
   * Create the application.
   */
  private NetworkFrame() throws Exception {
    initialize();
    networkStack = new LinkedList<>();
    networkService = new NetworkService();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() throws Exception {
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
    button_2.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        HardDiskFrame.getHardDiskFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });

    button_3 = new JButton("网络");
    button_3.setEnabled(false);
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
          for (Network cpu : networkStack) {
            networkService.insertOne(cpu);
          }
          networkStack.clear();
        } else if (status == 0) {
          if (!networkStack.isEmpty()) {
            networkService.insertOne(networkStack.pop());
          }
        } else {
          while (!networkStack.isEmpty()) {
            Network student = networkStack.pop();
            long begin = nowTime - status * 1000;
            if (student.getTime() < nowTime && student.getTime() > begin) {
              networkService.insertOne(student);
            } else {
              networkStack.push(student);
              break;
            }
          }
        }
      }
    });
    button_5.setBounds(619, 451, 75, 21);
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

    lblIp = new JLabel("ip地址：" + computerInfo.getIp());
    lblIp.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblIp.setBounds(280, 380, 261, 16);
    frame.getContentPane().add(lblIp);

    lblNewLabel = new JLabel("上传速度：" + computerInfo.getNetSpeed()[0]);
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel.setBounds(280, 410, 261, 16);
    frame.getContentPane().add(lblNewLabel);

    label_3 = new JLabel("下载速度：" + computerInfo.getNetSpeed()[1]);
    label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label_3.setBounds(280, 439, 265, 16);
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
        paintTable.printExcel(paintTable.getNetworkTitles());
      }
    });
    btnNewButton_2.setBounds(619, 414, 75, 21);
    frame.getContentPane().add(btnNewButton_2);
  }

  public void refresh(Network network) {
    if (networkStack.size() > 50) {
      networkStack.removeFirst();
    }
    if (categoryDataset.getColumnCount() > 50) {
      categoryDataset.clear();
    }
    networkStack.push(network);
    lblIp.setText("ip地址：" + network.getIp());
    lblNewLabel.setText("上传速度：" + String.format("%.2f", network.getUploadSpeed()) + "KB/s");
    label_3.setText("下载速度：" + String.format("%.2f", network.getDownloadSpeed()) + "KB/s");
    categoryDataset.addValue(network.getDownloadSpeed(), "download", network.getTime());
    categoryDataset.addValue(network.getUploadSpeed(), "upload", network.getTime());

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
