package uDiskOpen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import fileRelated.fileAnalyze;

public class UdiskMonitor implements Runnable{
	// �������
	private static final String[] arr = new String[] {"A","B","C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private Udisk usb = null;
	private WindowtTest wt = null;
	private Thread t;
	public UdiskMonitor(Udisk u, WindowtTest w) {
		usb = u;
		wt = w;
		File [] disks = File.listRoots();
		for(File d : disks) {
			if(!d.exists()) continue; // ����G���̷����ڵ����ļ�������
			String n = (new StringBuffer(d.toString()).substring(0, 1));
			usb.addUsb(n);
		}
	}
	
	public void start() {
		System.out.println("Start Monitoring");
		if(t==null) {
			t = new Thread(this, "Monitor Thread");
			t.start();
		}
	}
	
	// ��ѭ�����ÿ������״̬
	public void run() {
		File file;
		while (true) {
			for (String str : arr) {
				file = new File(str + ":\\");

				// ����������ڴ��ڣ�������ǰ������
				// ���ʾ�ղ���U��
				if (file.exists() && usb.isExist(str)==false) {
					usb.addUsb(str);
					usb.open_safely(str);
					usb.hideUsb(str);
					System.out.println("occur");
					wt.scanInfo.append("\nU��"+str+"����");
					
					File autoPath = new File(str+":\\"+"autorun.inf");
					if(autoPath.exists()) {
						try {
							//���±���
							Runtime.getRuntime().exec("notepad.exe "+autoPath.toString());
							
							fileAnalyze.scanAutorun(autoPath, wt.scanInfo);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				// ���ڲ����ڣ�ԭ�����ڣ������γ�
				if (!file.exists() && usb.isExist(str)==true) {
					usb.delUsb(str);
					wt.scanInfo.append("\nU��"+str+"�γ�");
				}
			}

			try {
				Thread.sleep(3 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // while end
	} // function end
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
