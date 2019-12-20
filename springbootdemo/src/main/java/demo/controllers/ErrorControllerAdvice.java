package demo.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ErrorControllerAdvice {
		
	@ExceptionHandler(value = Exception.class)
	@RequestMapping("/error")
    public String error(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("title", "Error");
        map.addAttribute("code", "sss");
        map.addAttribute("message", "error message");
        // return模板文件的名称，对应src/main/resources/templates/viewing.html
        return "error";
    }
	
}
