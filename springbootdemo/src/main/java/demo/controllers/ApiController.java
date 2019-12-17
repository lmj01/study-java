package demo.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
public class ApiController {

	@Value("${title}")
    private String title;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/api/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World")String name) {
		System.out.println("get greeting---");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@ResponseBody
    //@RequestMapping(value="/api/url", method=RequestMethod.POST, produces="application/json")
	@RequestMapping(value="/api/url", method=RequestMethod.POST)
    public RetData getApiData(@RequestBody InData inData) {
    	
		RetData ret = new RetData();
		ret.setMessage("ok");
		if (inData != null) {
			String inStr = String.format("---------------%d, %s", inData.getCode(), inData.getMessage());
	    	logger.info(inStr);
		}

    	logger.info("get api ");
    	    	
    	return ret;    	
    }


	@ResponseBody
    @RequestMapping(value="/api/json", method=RequestMethod.POST, produces="application/json")
    public RetData getJsonData(@RequestBody(required=false) InData inData) {
    	
    	RetData ret = new RetData();
    	
    	ret.setCode(inData.getCode());
    	ret.setMessage(inData.getMessage());
    	
    	return ret;
    }
    

    @RequestMapping(value="/api/form", method=RequestMethod.POST, produces="application/json")
    public void getFormData() {
    	
    	HttpHeaders headers = Util.getHttpHeaders();
    	
    }
}
