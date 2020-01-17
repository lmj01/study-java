package demo.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import common.Util;
import demo.entity.Greeting;
import demo.vo.InData;
import demo.vo.RetData;

/**
 * ResetController 不能返回页面或HTML， 返回的是Return的数据
 * @author meijie
 *
 */


@RestController
@RequestMapping("/api")
public class ApiController {

	@Value("${title}")
    private String title;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World")String name) {
		System.out.println("get greeting---");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@ResponseBody
    //@RequestMapping(value="/api/url?code=3", method=RequestMethod.POST, produces="application/json")
	@RequestMapping(value="/url", method=RequestMethod.POST)
    public RetData getApiData(@RequestParam Integer code) {
    	
		RetData ret = new RetData();
		ret.setMessage("ok");
		String inStr = String.format("---------------%d", code);
    	logger.info(inStr);
    	logger.info("get api ");
    	    	
    	return ret;    	
    }
	

	@ResponseBody
    @RequestMapping(value="/json", method=RequestMethod.POST, produces="application/json")
    public RetData getJsonData(@RequestBody(required=false) InData inData) {
    	
    	RetData ret = new RetData();
    	
    	ret.setCode(inData.getCode());
    	ret.setMessage(inData.getMessage());
    	
    	return ret;
    }
    

    @RequestMapping(value="/form", consumes="multipart/form-data", method=RequestMethod.POST, produces="application/json")
    public String getFormData(@RequestParam(value="name", required=true) final String name,
    						  @RequestParam(value="password", required=true) final String password,
    						  @RequestParam("docFiles") List<MultipartFile> docFiles) {
    	
    	//HttpHeaders headers = Util.getHttpHeaders();
    	
    	//String inStr = String.format("post in user data --------%s, %s", inData.getName(), inData.getPassword());
    	//logger.info(inStr);
    	
    	String inStr = String.format("post in user data --------%s, %s", name, password);
    	logger.info(inStr);
    	
    	return "ok";
    }
    
    @RequestMapping(value="/mp4", method=RequestMethod.POST, produces="application/octet-stream")
    @ResponseBody
    public byte[] getVideo(@RequestParam("uuid") String uuid) {
    	
    	logger.info(uuid);	
		try {
			ClassPathResource mp4File = new ClassPathResource("static/videos/test.mp4");
			File file = mp4File.getFile();
			byte[] bytes = new byte[(int)file.length()];
			FileInputStream fileStream = new FileInputStream(file);
			fileStream.read(bytes);
			fileStream.close();
			return bytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		
		return null;
    }
}
