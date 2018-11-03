package uDiskOpen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import fileRelated.fileAnalyze;

public class UdiskMonitor implements Runnable{
	// 定义磁盘
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
			if(!d.exists()) continue; // 比如G盘盘符存在但是文件不存在
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
	
	// 死循环检测每个磁盘状态
	public void run() {
		File file;
		while (true) {
			for (String str : arr) {
				file = new File(str + ":\\");

				// 如果磁盘现在存在，并且以前不存在
				// 则表示刚插上U盘
				if (file.exists() && usb.isExist(str)==false) {
					usb.addUsb(str);
					usb.open_safely(str);
					usb.hideUsb(str);
					System.out.println("occur");
					wt.scanInfo.append("\nU盘"+str+"插入");
					
					File autoPath = new File(str+":\\"+"autorun.inf");
					if(autoPath.exists()) {
						try {
							//记事本打开
							Runtime.getRuntime().exec("notepad.exe "+autoPath.toString());
							
							fileAnalyze.scanAutorun(autoPath, wt.scanInfo);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				// 现在不存在，原来存在，表明拔出
				if (!file.exists() && usb.isExist(str)==true) {
					usb.delUsb(str);
					wt.scanInfo.append("\nU盘"+str+"拔出");
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
