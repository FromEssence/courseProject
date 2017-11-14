package diffieHellman;

import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
public class DiffieHellman {
	private int q,a, Xa, Ya;
	
	private int quiPow(int a, int b, int n){

		int ans=1;
		a = a%n;
		while (b>0)
		{
		    if (b%2==1) ans=ans*a%n;//odd
		    b = b/2;
		    a = a*a%n;
		}
		return ans;
	}
	
	public boolean setQ(int q){
		for(int i=2; i<=(int)Math.sqrt(q); i++){
			if(q%i == 0){
				return false;
			}
		}
		this.q = q;
		return true;
		
	}
	
	public boolean setA(int a){
		this.a = a;
		return true;
	}
	
	
	public int getXa(){//返回私钥
		return Xa;
	}
	
	public int getYa(){//返回公钥
		return Ya;
	}
	public void generateKey(){//生成公钥和私钥
		Random rd = new Random();
		Xa = rd.ints(1,1, q).toArray()[0];
		Ya = quiPow(a, Xa, q);
	}
	public int getChatKey(int e){//生成会话密钥
		return quiPow(e, Xa, q);
	}
	public void showMenu(){//显示操作菜单
		System.out.println("请选择操作：\n"+"1.生成自己的公钥和私钥\n"+"2.生成会话密钥\n"+"-1.退出");
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		DiffieHellman dh = new DiffieHellman();
		dh.showMenu();
		int op = in.nextInt();
		boolean flag = false;
		while(op>0){
			if(op == 1){
				System.out.println("请输入素数q：");
				int q = in.nextInt();
				while(dh.setQ(q) == false){
					System.out.println("The q you input is not a prime, please input q again:");
					q = in.nextInt();
				}
				System.out.println("请输入q的一个原根a：");
				int a = in.nextInt();
				while(dh.setA(a) == false){
					System.out.println("The a you have input is not a primari root of q, input a again:");
					a = in.nextInt();
				}
				dh.generateKey();
				System.out.println("公钥:"+dh.getYa()+"\n私钥:"+dh.getXa());
				flag = true;
			}
			else if(op == 2){
				if(flag == false){
					System.out.println("您还没生成自己的密钥，请先进行操作1！");
					continue;
				}
				System.out.println("请输入对方的公钥:");
				int e = in.nextInt();
				System.out.println("会话密钥为:"+dh.getChatKey(e));
				
			}
			else {
				System.out.println("不存在的操作！请重新选择。");
			}
			dh.showMenu();
			op = in.nextInt();
		}
		System.out.println("Bye");
		in.close();
	}

}
