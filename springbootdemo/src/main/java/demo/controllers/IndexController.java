package demo.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
	@Value("${userName}")
    private String userName;

    @Value("${title}")
    private String title;
        
    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("name", "Study Java");
        map.addAttribute("title", title);
        return "index";
    }
    
}
