package com.meijie.study.Class;

public class Circle extends Shape {

	@Override
	public void Print(String str) {
		// TODO Auto-generated method stub
		String format = String.format("Child-Class: %s", str);
		super.Print(format);		
	}

}
