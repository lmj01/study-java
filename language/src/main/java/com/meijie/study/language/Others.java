package com.meijie.study.language;

public class Others {

	// test for two different object, 
	// for type 1 is odd 
	// for type 2 is even 
	// and do loop 
	public static int size = 0;
	public int valid(int idx) {
		boolean isEven = size % 2 == 0;
		int res = -1;
		int count = size / 2;
		if (idx > -1 && idx < size) {
			if (isEven) {
				res = idx + (size < 4 ? count : (count-1) * 2);	
			} else {
				res = idx + (size > 2 ? count * 2 : 0);
			}	
			//res = idx + (size < 4 ? count : (count-1) * 2);
			if (size > 2 && idx == 1) res += (isEven?0:-1);
			return res;
		}
		if (size > 0) return size - 1;
		return res;
	}

	public static void test() {
	
		Others a = new Others();
		Others.size = 1;
		String str = String.format("1 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 2;
		str = String.format("2 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 3;
		str = String.format("3 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 4;
		str = String.format("4 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 5;
		str = String.format("5 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 6;
		str = String.format("6 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 7;
		str = String.format("7 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
		Others.size = 8;
		str = String.format("8 item is %d, %d", a.valid(0), a.valid(1));
		System.out.println(str);
	}
}
