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
		
		JButton btnNewButton = new JButton("��ɨ��U������������");
		btnNewButton.setToolTipText("������������˫��U��һɲ�Ǿͻ�ִ�У�ǿ�ҽ���ɨ�裡");
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> usbs = usb.getAllUsb();
				if(usbs.size()==0) scanInfo.append("\nδ��⵽������ǽ����ǰ����Ŀ��ƶ��豸\n");
				for (String u :usbs) {
					File autoPath = new File(u+":\\"+"autorun.inf");
					System.out.println("��U��"+u+"������ɨ��");
					if(autoPath.exists()) {
						try {
							fileAnalyze.scanAutorun(autoPath, scanInfo);
						} catch (Exception ee) {
							// TODO Auto-generated catch block
							ee.printStackTrace();
						}
					}
					
					else {
						scanInfo.append("\nɨ����ɣ�û�������в�����");
					}
				}
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("��ȫ��U��");
		btnNewButton_1.setToolTipText("��ȫ��U�̿��Է�ֹU��������������ִ��");
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("����\"��ȫ��\"");
				
				ArrayList<String > uDisks = new ArrayList<String>();
				uDisks = usb.getAllUsb();
				
				for(String name : uDisks) {
					usb.open_safely(name);
				}
				
				if(uDisks.size()==0) {
					System.out.print("\n��U��\n");
				}
			}
		});
		
		JButton btnNewButton_2 = new JButton("��С������");
		btnNewButton_2.setToolTipText("�ҽ�ĬĬ�ػ���");
		panel.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mini();
			}
		});
		
		JButton btnNewButton_3 = new JButton("����������");
		btnNewButton_3.setToolTipText("�����ռ���ڴ漫С��ȴ�ܱ���U�̰�ȫ");
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
		scanInfo.setToolTipText("������ʾɨ�輰ɱ����Ϣ");
		scanInfo.setRows(40);
		scanInfo.setText("��ӭʹ��̫������ǽ��");
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

	      frame.dispose(); //������С��ʱdispose�ô���
	               
	      // �ж��Ƿ�֧��ϵͳ����
	      if (SystemTray.isSupported())
	      {
	         // ��ȡͼƬ���ڵ�URL
	         //URL url = WindowtTest.class.getResource();
	         // ʵ����ͼ�����
	         ImageIcon icon = new ImageIcon("img/timg.png");
	         // ���Image����
	         Image image = icon.getImage();
	         // ��������ͼ��
	         TrayIcon trayIcon = new TrayIcon(image);
	         // Ϊ����������������
	         trayIcon.addMouseListener(new MouseAdapter()
	         {
	            // ����¼�
	            public void mouseClicked(MouseEvent e)
	            {
	               // �ж��Ƿ񵥻������
	               if (e.getClickCount() == 1)
	               {
	                   //frame.repaint();
	                   frame.setVisible(true);//����ɼ�
	                   frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // ����ȫ��
	                   SystemTray.getSystemTray().remove(trayIcon);//����ͼ��ɾ��

	               }
	            }
	         });
	         // ��ӹ�����ʾ�ı�
	         trayIcon.setToolTip("̫��ɱ��");

//	         // Ϊ����ͼ��ӵ����˵�
//	         trayIcon.setPopupMenu(popupMenu);
	         // ���ϵͳ���̶���
	         SystemTray systemTray = SystemTray.getSystemTray();
	         try
	         {
	            // Ϊϵͳ���̼�����ͼ��
	            systemTray.add(trayIcon);
	         }
	         catch (Exception e)
	         {
	            e.printStackTrace();
	         }
	      }
	      else
	      {
	         JOptionPane.showMessageDialog(null, "����ϵͳ��֧�ֽ������С��ϵͳͼ�꣡");
	      }
	   }
	 
	 public String autoRun() {
		 
		 return System.getProperty("java.class.path");
		
	 }

}
