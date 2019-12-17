package demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorControllerAdvice {
	
//	@ResponseBody
//	@ExceptionHandler(value = Exception.class)
//	public Map errorHhandler(Exception ex) {
//		
//		Map map = new HashMap();
//		
//		map.put("code", 0);
//		map.put("msg", ex.getMessage());
//		
//		return map;
//		
//	}
	
	@ExceptionHandler(value = Exception.class)
	@RequestMapping("/error")
    public String error(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("title", "Error");
        map.addAttribute("code", 0);
        map.addAttribute("message", "error message");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "error";
    }
	
}
