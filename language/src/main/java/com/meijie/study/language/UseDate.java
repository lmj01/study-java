package com.meijie.study.language;

import java.util.Date;

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
		d1 = new Date("2019/12/02");
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
	
	public static void test() {
		UseDate ud = new UseDate();
		
		ud.demo1();
	}
}
