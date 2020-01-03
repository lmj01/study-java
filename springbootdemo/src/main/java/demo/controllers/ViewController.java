package demo.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import common.Util;


@Controller
@RequestMapping("/view")
public class ViewController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/")
    public String index(ModelMap map) {
		map.addAttribute("title", "View | Api");
        return "view/index";
    }
	
	
	@RequestMapping("/api")
    public String viewApi(ModelMap map) {
		map.addAttribute("title", "Demo | Api");
        return "view/api";
    }
	
    
	// http://localhost:9090/pdf?uuid=1
    @RequestMapping(value="/pdf", method=RequestMethod.GET, produces="application/pdf")
    public ResponseEntity<InputStreamResource> pdf(@RequestParam("uuid") String uuid) {
    	
    	logger.info(uuid);
    	
		HttpHeaders headers = Util.getHttpHeaders();    		
		//headers.add("Content-Disposition", "attachment;filename=\"" +  + "\"");
	
		ClassPathResource pdfFile = new ClassPathResource("static/standalone1/test.pdf");
		
		logger.info("get pdf");
		
		try {
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(pdfFile.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		
		return null;
    }
}
