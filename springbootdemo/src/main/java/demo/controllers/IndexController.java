package demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.ConfigBean;


@Controller
public class IndexController {
	@Value("${userName}")
    private String userName;

    @Value("${title}")
    private String title;
    
    @Autowired
    ConfigBean configBean;
        
    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("name", configBean.getName());
        map.addAttribute("title", title);
        return "index";
    }
    
}
