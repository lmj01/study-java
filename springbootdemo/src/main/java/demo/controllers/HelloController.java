package demo.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class HelloController {
	@Value("${userName}")
    private String userName;

    @Value("${title}")
    private String title;

    private static int count = 0;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
    	System.out.println(String.format("count now is %d", count++));
        map.addAttribute("name", "Study Java");
        map.addAttribute("title", title);
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "index";
    }
    
    @RequestMapping("/demo")
    public String demo(ModelMap map) {
        map.addAttribute("name", "demo");
        map.addAttribute("title", "demo");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "demo";
    }
    
    @RequestMapping("/demo2")
    public String demoThird(ModelMap map) {
        map.addAttribute("name", "demo third library");
        map.addAttribute("title", "demo third library ");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "demo2";
    }
    
    @RequestMapping("/hello")
    public String hello(ModelMap map) {
        map.addAttribute("name", "hello");
        map.addAttribute("title", "hello");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "hello";
    }
    
    @RequestMapping("/api")
    public String index2(ModelMap map) {
		map.addAttribute("title", "api");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "apiView";
    }
    
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
