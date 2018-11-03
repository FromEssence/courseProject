package uDiskOpen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextArea;

import fileRelated.fileAnalyze;

import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WindowtTest {

	private JFrame frame;
	JTextArea scanInfo = null;
	private Udisk usb = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Udisk usb = new Udisk();
				
				try {
					WindowtTest window = new WindowtTest(usb);
					window.frame.setVisible(true);
					
					UdiskMonitor um = new UdiskMonitor(usb, window);
					um.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//System.out.println("Hello");
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowtTest(Udisk u) {
		usb = u;
		scanInfo = new JTextArea();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 250, 154));
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("云扫描U盘自启动病毒");
		btnNewButton.setToolTipText("自启动病毒在双击U盘一刹那就会执行，强烈建议扫描！");
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> usbs = usb.getAllUsb();
				if(usbs.size()==0) scanInfo.append("\n未检测到本防火墙运行前插入的可移动设备\n");
				for (String u :usbs) {
					File autoPath = new File(u+":\\"+"autorun.inf");
					System.out.println("对U盘"+u+"进行云扫描");
					if(autoPath.exists()) {
						try {
							fileAnalyze.scanAutorun(autoPath, scanInfo);
						} catch (Exception ee) {
							// TODO Auto-generated catch block
							ee.printStackTrace();
						}
					}
					
					else {
						scanInfo.append("\n扫描完成，没有自运行病毒！");
					}
				}
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("安全打开U盘");
		btnNewButton_1.setToolTipText("安全打开U盘可以防止U盘自启动病毒的执行");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("单击\"安全打开\"");
				
				ArrayList<String > uDisks = new ArrayList<String>();
				uDisks = usb.getAllUsb();
				
				for(String name : uDisks) {
					usb.open_safely(name);
				}
				
				if(uDisks.size()==0) {
					System.out.print("\n无U盘\n");
				}
			}
		});
		
		JButton btnNewButton_2 = new JButton("缩小至托盘");
		btnNewButton_2.setToolTipText("我将默默守护你");
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mini();
			}
		});
		
		JButton btnNewButton_3 = new JButton("开机自启动");
		btnNewButton_3.setToolTipText("本软件占用内存极小，却能保护U盘安全");
		panel.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String regKey = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run";  
			    String myAppName =  "TaiChi";
			    String exePath = System.getProperty("java.class.path"); 
			    //"\"D:\\Program Files (x86)\\love\\HelloWorld.exe\"";  
			    try {
					Runtime.getRuntime().exec("reg "+"add "+regKey+" /v "+myAppName+" /t reg_sz /d "+"\""+exePath+"\""+" /f");
					scanInfo.append("\n"+"reg "+"add "+regKey+" /v "+myAppName+" /t reg_sz /d "+"\""+exePath+"\""+" /f");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		
//		JTextArea scanInfo = new JTextArea();
		scanInfo.setToolTipText("这里显示扫描及杀毒信息");
		scanInfo.setRows(40);
		scanInfo.setText("欢迎使用太极防火墙！");
		scanInfo.setColumns(25);
		frame.getContentPane().add(scanInfo);
		frame.setBounds(100, 100, 503, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 scanInfo.append("\n"+autoRun()+"\n");
				 
			}
		});
		
		
	}
	
	 public void mini()
	   {

	      frame.dispose(); //窗口最小化时dispose该窗口
	               
	      // 判断是否支持系统托盘
	      if (SystemTray.isSupported())
	      {
	         // 获取图片所在的URL
	         //URL url = WindowtTest.class.getResource();
	         // 实例化图像对象
	         ImageIcon icon = new ImageIcon("img/timg.png");
	         // 获得Image对象
	         Image image = icon.getImage();
	         // 创建托盘图标
	         TrayIcon trayIcon = new TrayIcon(image);
	         // 为托盘添加鼠标适配器
	         trayIcon.addMouseListener(new MouseAdapter()
	         {
	            // 鼠标事件
	            public void mouseClicked(MouseEvent e)
	            {
	               // 判断是否单击了鼠标
	               if (e.getClickCount() == 1)
	               {
	                   //frame.repaint();
	                   frame.setVisible(true);//界面可见
	                   frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 界面全屏
	                   SystemTray.getSystemTray().remove(trayIcon);//托盘图标删除

	               }
	            }
	         });
	         // 添加工具提示文本
	         trayIcon.setToolTip("太极杀毒");

//	         // 为托盘图标加弹出菜弹
//	         trayIcon.setPopupMenu(popupMenu);
	         // 获得系统托盘对象
	         SystemTray systemTray = SystemTray.getSystemTray();
	         try
	         {
	            // 为系统托盘加托盘图标
	            systemTray.add(trayIcon);
	         }
	         catch (Exception e)
	         {
	            e.printStackTrace();
	         }
	      }
	      else
	      {
	         JOptionPane.showMessageDialog(null, "您的系统不支持将软件缩小到系统图标！");
	      }
	   }
	 
	 public String autoRun() {
		 
		 return System.getProperty("java.class.path");
		
	 }

}
