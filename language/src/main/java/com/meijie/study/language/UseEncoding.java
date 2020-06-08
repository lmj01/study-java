package com.meijie.study.language;

import java.io.UnsupportedEncodingException;

public class UseEncoding {

	public static void test() throws Exception {
		char a = '我';
		System.out.println(a);
		char b = 'a'+'b';
		System.out.println(b);
		
		char c = 'a';
		System.out.println(Character.isLowerCase(c));
		System.out.println(Character.isUpperCase(c));
		System.out.println(c);
		
		char d = '2';
		System.out.println(d);
		
//		System.out.println(System.getProperty(key));
		String str = new String("我是中国人");
		System.out.println(new String(str.getBytes("GBK")));
		System.out.println(new String(str.getBytes("UTF-8")));
	}
}
