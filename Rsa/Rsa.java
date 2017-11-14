package Rsa;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Rsa {
	private BigInteger n;
	private BigInteger phin;
	private BigInteger e;
	private BigInteger d;
	
	private void generateKey(){
		Random rnd = new Random(new Date().getTime());
		BigInteger p = BigInteger.probablePrime(1024, rnd);
		BigInteger q = BigInteger.probablePrime(1024, rnd);
		n = p.multiply(q);
		while(p.equals(q)){// avoid generating same primes
			q = BigInteger.probablePrime(1024, rnd);
		}
		phin = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
		e = BigInteger.probablePrime(1024, rnd);
		while(phin.gcd(e).equals(e)){
			e = BigInteger.probablePrime(1024, rnd);
		}
		d = e.modInverse(phin);
	}
	
	public BigInteger encry(int m){
		generateKey();
		BigInteger c= new BigInteger("0");
		BigInteger M = BigInteger.valueOf((long)m);
		//c = M.modPow(e, n);
		quickPow QP = new quickPow();
		c = QP.quiPow(M, e, n);
		return c;
	}
	
	public BigInteger decry(BigInteger c){
		quickPow QP = new quickPow();
		BigInteger m = QP.quiPow(c, d, n);
		return m;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		Rsa rsa = new Rsa();
		System.out.println("Please input a positive integer:");
		int m = in.nextInt();
		BigInteger c = rsa.encry(m);
		BigInteger M = rsa.decry(c);
		System.out.println("After Encry is:" + c.toString());
		System.out.println("After Decry is:" + M.toString());
		in.close();
	}

}
