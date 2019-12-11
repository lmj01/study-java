package Pdf;

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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	@Value("${userName}")
    private String userName;

    @Value("${title}")
    private String title;

    private static int count = 0;
    private static final Logger logger = 
			LoggerFactory.getLogger(PdfApplication.class);
    
    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
    	System.out.println(String.format("count now is %d", count++));
        map.addAttribute("name", userName);
        map.addAttribute("title", title);
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "viewing";
    }
    
    @RequestMapping(value="/pdf", method=RequestMethod.GET, produces="application/pdf")
    public ResponseEntity<InputStreamResource> pdf(@RequestParam("uuid") String uuid) {
    	
    	logger.info(uuid);
    	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("charset", "utf-8");    		
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
