package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class})
public class DemoApplication {
	
	public static void main(String[] args) {
	
		Logger logger = LoggerFactory.getLogger(DemoApplication.class);
		
		SpringApplication.run(DemoApplication.class, args);
				
		if (logger.isTraceEnabled()) {
			logger.trace("test---trace message");	
		}
		if (logger.isDebugEnabled()) {
			logger.debug("test---debug message");				
		}
		if (logger.isInfoEnabled()) {
			logger.info("test---info message");
		}
		if (logger.isWarnEnabled()) {
			logger.warn("test---warn message");
		}
		if (logger.isErrorEnabled()) {
			logger.error("test---error message");
		}
	}

}
