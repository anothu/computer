package indi.me.computer.frame;

import indi.me.computer.service.LoginService;
import indi.me.computer.utils.ComputerInfo;
import indi.me.computer.utils.PaintTable;
import indi.me.computer.utils.Settings;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import lombok.SneakyThrows;
import org.hyperic.sigar.SigarException;


public class StartFrame {

  private static volatile StartFrame startFrame = null;

  public static StartFrame getStartFrame() {
    if (startFrame == null) {
      synchronized (StartFrame.class) {
        if (startFrame == null) {
          try {
            startFrame = new StartFrame();
          } catch (SigarException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return startFrame;
  }

  public JFrame frame;
  private JTextField textField;
  private JTextField textField_1;
  private JLabel name;
  private JLabel password;
  private JButton button;
  private JButton btnNewButton;
  private JButton btnNewButton_1;
  private JButton button_1;
  private JButton button_2;
  private JButton button_3;
  private JMenu button_4;
  private JButton button_5;
  private JButton button_6;
  private ComputerInfo computerInfo = new ComputerInfo();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          getStartFrame().frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  private StartFrame() throws SigarException, InterruptedException {
    initialize();
    beforeLogin();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() throws SigarException, InterruptedException {
    frame = new JFrame();
    frame.setBounds(100, 100, 700, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    JMenuBar menuBar = new JMenuBar();
    menuBar.setToolTipText("os");
    menuBar.setBounds(0, 0, 728, 22);
    frame.getContentPane().add(menuBar);

    button_1 = new JButton("概览");
    button_1.setEnabled(false);
    button_1.addActionListener(new ActionListener() {
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
    button_6.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SettingsFrame.getSettingsFrame().frame.setVisible(true);
        frame.setVisible(false);
      }
    });
    menuBar.add(button_6);
    menuBar.add(button_4);
    JLabel OS = new JLabel("操作系统：" + computerInfo.getOS() + computerInfo.getOSVersion());
    OS.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    OS.setBounds(54, 65, 226, 16);
    frame.getContentPane().add(OS);

    JLabel cpu = new JLabel("处理器：" + computerInfo.getCpuName());
    cpu.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    cpu.setBounds(54, 151, 254, 16);
    frame.getContentPane().add(cpu);

    JLabel videoCard = new JLabel("内存：" + computerInfo.getTotalMemory() + "GB");
    videoCard.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    videoCard.setBounds(54, 240, 161, 16);
    frame.getContentPane().add(videoCard);

    JLabel lblNewLabel = new JLabel("硬盘：" + computerInfo.getHardDisk().getTotalAmount() + "GB");
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    lblNewLabel.setBounds(54, 321, 261, 16);
    frame.getContentPane().add(lblNewLabel);

  }

  private void beforeLogin() {
    name = new JLabel("账号：");
    name.setBounds(201, 430, 61, 16);
    frame.getContentPane().add(name);

    textField = new JTextField();
    textField.setBounds(240, 425, 130, 26);
    frame.getContentPane().add(textField);
    textField.setColumns(10);

    password = new JLabel("密码：");
    password.setBounds(382, 430, 61, 16);
    frame.getContentPane().add(password);

    textField_1 = new JPasswordField();
    textField_1.setBounds(423, 425, 130, 26);
    frame.getContentPane().add(textField_1);
    textField_1.setColumns(10);

    LoginService loginService = new LoginService();
    button = new JButton("登陆");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int count = loginService.Login(textField.getText(), textField_1.getText());
        if (count != 0) {
          Settings.isLogin = true;
          afterLogin();
          JOptionPane.showMessageDialog(frame, "登陆成功", "success", JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(frame, "密码错误。请重试", "fail", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    button.setBounds(565, 425, 117, 29);
    frame.getContentPane().add(button);
  }

  private void afterLogin() {
    frame.remove(name);
    frame.remove(password);
    frame.remove(textField);
    frame.remove(textField_1);
    frame.remove(button);
    JButton buttonn = new JButton("注销");
    buttonn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Settings.isLogin = false;
        frame.remove(buttonn);
        beforeLogin();
        frame.repaint();
        JOptionPane.showMessageDialog(frame, "注销成功", "success", JOptionPane.INFORMATION_MESSAGE);
      }
    });
    buttonn.setBounds(565, 425, 117, 29);
    frame.getContentPane().add(buttonn);
    frame.repaint();
  }
}
