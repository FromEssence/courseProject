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
	
	
	public int getXa(){//����˽Կ
		return Xa;
	}
	
	public int getYa(){//���ع�Կ
		return Ya;
	}
	public void generateKey(){//���ɹ�Կ��˽Կ
		Random rd = new Random();
		Xa = rd.ints(1,1, q).toArray()[0];
		Ya = quiPow(a, Xa, q);
	}
	public int getChatKey(int e){//���ɻỰ��Կ
		return quiPow(e, Xa, q);
	}
	public void showMenu(){//��ʾ�����˵�
		System.out.println("��ѡ�������\n"+"1.�����Լ��Ĺ�Կ��˽Կ\n"+"2.���ɻỰ��Կ\n"+"-1.�˳�");
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		DiffieHellman dh = new DiffieHellman();
		dh.showMenu();
		int op = in.nextInt();
		boolean flag = false;
		while(op>0){
			if(op == 1){
				System.out.println("����������q��");
				int q = in.nextInt();
				while(dh.setQ(q) == false){
					System.out.println("The q you input is not a prime, please input q again:");
					q = in.nextInt();
				}
				System.out.println("������q��һ��ԭ��a��");
				int a = in.nextInt();
				while(dh.setA(a) == false){
					System.out.println("The a you have input is not a primari root of q, input a again:");
					a = in.nextInt();
				}
				dh.generateKey();
				System.out.println("��Կ:"+dh.getYa()+"\n˽Կ:"+dh.getXa());
				flag = true;
			}
			else if(op == 2){
				if(flag == false){
					System.out.println("����û�����Լ�����Կ�����Ƚ��в���1��");
					continue;
				}
				System.out.println("������Է��Ĺ�Կ:");
				int e = in.nextInt();
				System.out.println("�Ự��ԿΪ:"+dh.getChatKey(e));
				
			}
			else {
				System.out.println("�����ڵĲ�����������ѡ��");
			}
			dh.showMenu();
			op = in.nextInt();
		}
		System.out.println("Bye");
		in.close();
	}

}
