package com.meijie.study.language;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UseTime {

	private LocalDate today;
	
	public LocalDate getToday() {
		return today;
	}

	public void setToday(LocalDate today) {
		this.today = today;
	}	
	
	@Override
	public String toString() {
		//return "UseTime [today=" + today + ", toString()=" + super.toString() + "]";
		return String.format("ios date time: %s , %d, %d, %d,", 
				//today.format(DateTimeFormatter.ISO_DATE_TIME)
				today.toString(), today.getYear(), today.getMonth().getValue(), today.getDayOfMonth()
		);
	}

	public static void test() {
		UseTime ut = new UseTime();
		
		ut.setToday(LocalDate.now());
		
		String res = String.format("now is %s, %s", 
				ut.getToday().toString(), 
				ut.toString()
		);		
				
		System.out.println(res);
		
	}
}
