package uDiskOpen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Udisk {
	
	private ArrayList<String> uName = new ArrayList<String>();
	private HashMap<String, Boolean> status = new HashMap<String, Boolean>();//值表示是否被隐藏
	private Long tolStatus = 0l;
	
	public Boolean isExist(String name) {
		return status.get(name) != null;
	}
	
	public Boolean isHidden(String name) {
		return status.get(name);
	}
	
	public ArrayList<String> getAllUsb(){
		
		ArrayList<String> res = new ArrayList<String>();
		for(String key : status.keySet()) {
			if(status.get(key) == true) // 被隐藏，说明是U盘
				res.add(key);
		}
		
		System.out.println(status.size());
		return res;
	}
	
	public Integer addUsb(String name) {
		if(!isExist(name)) {
			status.put(name, false);
		}
		
		return status.size();
	}
	
	public Integer delUsb(String name) {
		if(isExist(name)) {
			if(status.get(name)==true)
				showUsb(name);//注意恢复隐藏标志位
			status.remove(name);
		}
		System.out.println("U盘"+name+"拔出");
		 
		return status.size();
	}
	
	public void hideUsb(String name) {
	
		try {
			
			String commandPrefix = "cmd.exe /k reg add ";
			String key = "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\"";
			String kName = "NoDrives";
		
			int bitOffSet = name.charAt(0) - 'a' >= 0? name.charAt(0) - 'a': name.charAt(0)-'A';
			if(status.get(name)==null || status.get(name)==false) {
				tolStatus += 1l << bitOffSet;
				status.put(name, true);
			}
			
			Runtime.getRuntime().exec(commandPrefix + key + " /v " + kName + " /t REG_DWORD /d " + tolStatus.toString()+" /f");
			
			System.out.println(bitOffSet);
			
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("注册表修改出现错误"+e);
			e.printStackTrace();
		}
	}
	
	public void showUsb(String name) {
		try {
			String commandPrefix = "cmd.exe /k reg add ";
			String key = "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\"";
			String kName = "NoDrives";
		
			int bitOffSet = name.charAt(0) - 'a' >= 0? name.charAt(0) - 'a': name.charAt(0)-'A';
			if(status.get(name)==null || status.get(name)==false) {
				tolStatus -= 1l << bitOffSet;
			}
			
			Runtime.getRuntime().exec(commandPrefix + key + " /v " + kName + " /t REG_DWORD /d " + tolStatus.toString()+" /f");
			
			System.out.println(bitOffSet);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("注册表修改出现错误"+e);
			e.printStackTrace();
		}
	}
	
	public void showAllUsb() {
		try {
			String commandPrefix = "cmd.exe /k reg add ";
			String key = "\"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer\"";
			String kName = "NoDrives";
			
			Runtime.getRuntime().exec(commandPrefix + key + " /v " + kName + " /t REG_DWORD /d " + "0 /f");
			
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("注册表修改出现错误"+e);
			e.printStackTrace();
		}
	}
	
	public void open_safely(String name) {
		try {
			Runtime.getRuntime().exec("cmd /c start explorer "+name+":\\");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Udisk usb = new Udisk();
		usb.open_safely("f");
		usb.hideUsb("E");
		usb.showAllUsb();
	}

}
