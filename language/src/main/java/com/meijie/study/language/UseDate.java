package com.meijie.study.language;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UseDate {

	class ObjectOne {
		private Date day;

		public Date getDay() {
			return day;
		}

		public void setDay(Date day) {
			this.day = day;
		}		
	}
	
	class ObjectTwo {
		private Date day;

		public Date getDay() {
			return day;
		}

		public void setDay(Date day) {
			this.day = day;
		}
		
	}
	
	private Date d1;	
	
	public void demo1() {
		d1 = new Date("2019/12/02 09:02:01");
		System.out.println(String.format("d1 - %s \n %s, \n %s", 
				d1.toString(),
				d1.toGMTString(),
				d1.toLocaleString()
			));
		
		ObjectOne one = new ObjectOne();
		ObjectTwo two = new ObjectTwo();
		
		one.setDay(d1);
		two.setDay(d1);
		System.out.println(String.format("-- %s, %s, ", 
				one.getDay().toString(), two.getDay().toString()));
		
	}
	
	public void demo2() {
		Date d1 = new Date("2019/12/02 09:02:01");
		Date d2 = new Date("2019/12/02 10:12:02");
		Date d3 = new Date("2019/12/12 19:12:02");
		Date d4 = new Date("2019/12/12 12:05:02");
		Date d5 = new Date("2019/12/12 09:02:02");
		
		List<Date> all = new ArrayList<>();
		all.add(d4);
		all.add(d3);
		all.add(d2);
		all.add(d1);
		all.add(d5);
		
		all.sort((a,b)->a.compareTo(b));
		System.out.println("positive sequence : a compare to b");
		for (Date d : all) {
			System.out.println(d.toString());
		}
		all.sort((a,b)->b.compareTo(a));
		System.out.println("reverse order : b compare to a");
		for (Date d : all) {
			System.out.println(d.toString());
		}
	}
	
	public static void test() {
		UseDate ud = new UseDate();
		
		ud.demo1();
		ud.demo2();
		
	}
}
