package fileRelated;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class fileAnalyze {
	
	private static String[] suffixname = {".exe",".bat",".com",".vbs",".pif",".scr",".lnk"};
	
	//分析Autorun.inf文件，输出分析报告
	//经测试，即使文件隐藏，依然可以读取
	//以File作为输入源File->FileReader
	public static void scanAutorun(File source, JTextArea textarea) throws Exception { 
		textarea.append("\n正在扫描autorun.inf文件...");
		FileReader m=new FileReader(source);
		BufferedReader reader=new BufferedReader(m);//缓冲，提高读取效率
		ArrayList<String> virusPaths = new ArrayList<String>();
		String rootPath = source.getParent();
		
		virusPaths.add("python");
		virusPaths.add("cloud_test.py");
		
		
		int count = 0;
		
		while(true) {
			String nextline=reader.readLine(); //按行读取
			if(nextline==null) break;
			//System.out.println("got:"+nextline);
			
			//解析
			for(String child:suffixname) {
				//System.out.println(child);
				
				int index = nextline.indexOf(child,0); //匹配到"."的下标
				//System.out.println(index);
				while(index != -1) {
					System.out.println("get"+child+" "+index);
					StringBuffer strbuf = new StringBuffer(child);
					strbuf.reverse();
					//strbuf.append(nextline.charAt(0));
					for(int i=index-1; i>=0 && nextline.charAt(i) != ' '&& nextline.charAt(i) != ','&&nextline.charAt(i) != '='; i--) {
						strbuf.append(nextline.charAt(i));
					}
					strbuf.reverse();
					String ans = strbuf.toString();
					System.out.println(ans);
					
					if(new File(rootPath+ans).exists())
						virusPaths.add(rootPath+ans);
					//textarea.append("\n发现可疑病毒："+ans);
					//Desktop.getDesktop().open(new File(rootPath+ans));
					
					index = index+child.length();
					count++;
					
					index = nextline.indexOf(child,index);
					
				}
			}
		} //while
		if(virusPaths.size()>2) {
			textarea.append("\n共发现可疑病毒："+(virusPaths.size()-2)+"个，即将进行云扫描...");
			//System.out.println("\n共发现可疑病毒："+count+"个，即将进行云扫描...");
			reader.close();
			
			String[] scanArgs = virusPaths.toArray(new String[virusPaths.size()]);
			System.out.println("扫描参数"+scanArgs[1].toString());
			
			Process pr = Runtime.getRuntime().exec(scanArgs);
			pr.waitFor();
			for(int i = 2; i<virusPaths.size(); i++) {
				File nf = new File(virusPaths.get(i));
				nf.delete();
			}
			
			m=new FileReader(new File("report.txt"));
			reader = new BufferedReader(m);
			
			while(true) {
				String nextline=reader.readLine(); //按行读取
				
				if(nextline==null) break;
				textarea.append("\n"+nextline);
			}
			System.out.println("end");
		}
		else {
			textarea.append("\n扫描完成，没有发现自运行病毒！");
		}
	}
	
 
	public static void main(String[] args){	
		try {
			JTextArea test = new JTextArea();
			scanAutorun(new File("D:/aaa.inf"), test);
			
			String argPy[] = new String[] {"python","cloud_test.py","D:\\desktop\\temp\\getTestExe.exe"};
//			try{
//				Process pr = Runtime.getRuntime().exec(argPy);
//				
//				pr.waitFor();
//				System.out.println("end");
//				
//		}catch(Exception e){
//				e.printStackTrace();
//			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
