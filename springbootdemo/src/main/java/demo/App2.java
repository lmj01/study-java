package demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
	
	public static void main(String[] args) {
		
		try {
			
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanConfig.xml");
		    SimpleBean bean = context.getBean(SimpleBean.class);
		    bean.send();
		    context.close();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}

}
