package demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/demo")
public class DemoController {
    
    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("title", "Demo");
        return "demo/index";
    }
    
    @RequestMapping("/sort")
    public String demo(ModelMap map) {
        map.addAttribute("title", "Demo|sort");
        return "demo/sort";
    }
    
    @RequestMapping("/uuid")
    public String demoThird(ModelMap map) {
        map.addAttribute("title", "uuid");
        return "demo/uuid";
    }
    
    @RequestMapping("/progress")
    public String demoProgress(ModelMap map) {
        map.addAttribute("title", "Progress");
        return "demo/progress";
    }
        
    @RequestMapping("/pdf")
    public String hello(ModelMap map) {
        map.addAttribute("name", "hello");
        map.addAttribute("title", "Demo | Pdf");
        return "demo/pdf";
    }
    
    
}
