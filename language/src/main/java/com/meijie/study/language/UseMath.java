package com.meijie.study.language;

public class UseMath {

	
	
	public static void test() {
		Integer code = (int) Math.floor(Math.random()*(1999999-1000000+1)
				//+1000000
				);
		System.out.println(String.format("random code is %d", code));
	}
}
