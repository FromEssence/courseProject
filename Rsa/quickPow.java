package Rsa;

import java.math.BigInteger;

public class quickPow {
	public BigInteger quiPow(BigInteger a, BigInteger b, BigInteger n){

		BigInteger ans=new BigInteger("1");
		a = a.mod(n);
		while (b.equals(BigInteger.ZERO) == false)
		{
		    if (b.mod(new BigInteger("2")).equals(BigInteger.ONE)) ans=ans.multiply(a).mod(n);//odd
		    b = b.divide(new BigInteger("2"));
		    a = a.multiply(a).mod(n);
		}
		return ans;
	}

}
