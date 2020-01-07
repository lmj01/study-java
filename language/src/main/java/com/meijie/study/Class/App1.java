package com.meijie.study.Class;

public class App1 {
	
	public static void main(String[] args) {
		try {
			Class obj1 = Class.forName("Shape");
		
			Shape shape = new Shape();
			Class obj2 = shape.getClass();
			shape.Print("new object");
			
			// 实例对象
			Object ShapeInstance = obj1.newInstance(); // 只能返回Object
			
			Class<Shape> obj3 = Shape.class;
			Shape ShapeInstance2 = obj3.newInstance();
			ShapeInstance2.Print("new instance");
			// 任意泛型
			Class<?> objAny = Shape.class;
			objAny = int.class;
			objAny = double.class;
			
			// 通过子类获取父类的实例化的专用语法
			Class<Circle> circle = Circle.class;
			Class<? super Circle> superClass = circle.getSuperclass();
			
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
