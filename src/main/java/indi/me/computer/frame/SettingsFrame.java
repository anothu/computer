package indi.me.computer.frame;

import indi.me.computer.utils.PaintTable;
import indi.me.computer.utils.Settings;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import lombok.SneakyThrows;

public class SettingsFrame {


  private static volatile SettingsFrame settingsFrame = null;

  public static SettingsFrame getSettingsFrame() {
    if (settingsFrame == null) {
      synchronized (SettingsFrame.class) {
        if (settingsFrame == null) {
          settingsFrame = new SettingsFrame();
        }
      }
    }
    return settingsFrame;
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
  private JButton btnNewButton_2;
  private HashMap<String, Integer> map;

  /**
   * Create the application.
   */
  private SettingsFrame() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
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

    button_6 = new JButton("设置");
    button_6.setEnabled(false);
    button_6.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SettingsFrame.getSettingsFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });
    menuBar.add(button_6);
    menuBar.add(button_4);

    lblIp = new JLabel("cpu数据刷新时间");
    lblIp.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblIp.setBounds(109, 110, 141, 16);
    frame.getContentPane().add(lblIp);

    lblNewLabel = new JLabel("内存数据刷新时间");
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel.setBounds(109, 171, 141, 16);
    frame.getContentPane().add(lblNewLabel);

    label_3 = new JLabel("硬盘数据刷新时间");
    label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    label_3.setBounds(109, 238, 130, 16);
    frame.getContentPane().add(label_3);

    JComboBox comboBox = new JComboBox();
    comboBox.setBounds(262, 107, 100, 27);
    comboBox.addItem("1s一次");
    comboBox.addItem("3s一次");
    comboBox.addItem("5s一次");
    frame.getContentPane().add(comboBox);

    JComboBox comboBox_1 = new JComboBox();
    comboBox_1.setBounds(262, 168, 100, 27);
    comboBox_1.addItem("1s一次");
    comboBox_1.addItem("3s一次");
    comboBox_1.addItem("5s一次");
    frame.getContentPane().add(comboBox_1);

    JComboBox comboBox_2 = new JComboBox();
    comboBox_2.setBounds(262, 235, 100, 27);
    comboBox_2.addItem("1s一次");
    comboBox_2.addItem("3s一次");
    comboBox_2.addItem("5s一次");
    frame.getContentPane().add(comboBox_2);

    JLabel lblNewLabel_1 = new JLabel("网络数据刷新时间");
    lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel_1.setBounds(109, 307, 130, 16);
    frame.getContentPane().add(lblNewLabel_1);

    JComboBox comboBox_3 = new JComboBox();
    comboBox_3.setBounds(262, 304, 100, 27);
    comboBox_3.addItem("1s一次");
    comboBox_3.addItem("3s一次");
    comboBox_3.addItem("5s一次");
    frame.getContentPane().add(comboBox_3);

    map = new HashMap<>();
    map.put("最近一条数据", 0);
    map.put("5s之内", 5);
    map.put("30s之内", 30);
    map.put("1分钟之内", 60);
    map.put("所有缓存数据", Integer.MAX_VALUE);
    map.put("1s一次", 1);
    map.put("3s一次", 3);
    map.put("5s一次", 5);

    btnNewButton_2 = new JButton("确认");
    btnNewButton_2.setBounds(303, 407, 117, 29);
    btnNewButton_2.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        Settings.cpuRefresh = map.get(comboBox.getSelectedItem());
        Settings.HardRefresh = map.get(comboBox_1.getSelectedItem());
        Settings.MemeoryRefresh = map.get(comboBox_2.getSelectedItem());
        Settings.NetworkRefresh = map.get(comboBox_3.getSelectedItem());
        JOptionPane.showMessageDialog(frame, "更改完成", "sucess", JOptionPane.INFORMATION_MESSAGE);
      }
    });
    frame.getContentPane().add(btnNewButton_2);

    JButton button = new JButton("设置存储路径");
    button.setBounds(577, 32, 117, 29);
    button.addActionListener(new ActionListener() {
      @SneakyThrows
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showDialog(new JLabel(), "选择");
        jfc.setVisible(true);
        Settings.location = jfc.getSelectedFile().getAbsolutePath();
      }
    });
    frame.getContentPane().add(button);
  }


}
