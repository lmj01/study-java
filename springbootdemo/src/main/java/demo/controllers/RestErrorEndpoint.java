package demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

public class RestErrorEndpoint extends BasicErrorController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public RestErrorEndpoint(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
		super(errorAttributes, errorProperties);
		// TODO Auto-generated constructor stub
		
		logger.info("rest error ----");
	}

}
