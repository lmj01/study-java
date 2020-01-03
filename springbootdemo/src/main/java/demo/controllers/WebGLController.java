package demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/webgl")
public class WebGLController {
	
    private static int count = 0;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("name", "WebGL");
        map.addAttribute("title", "WebGL");
        return "webgl/index";
    }
    
    @RequestMapping("/draco/decode")
    public String dracoDecode(ModelMap map) {
        map.addAttribute("title", "WebGL | Draco Decode");
        return "webgl/dracoDecode";
    }
    
    @RequestMapping("/draco/encode")
    public String dracoEncode(ModelMap map) {
        map.addAttribute("title", "WebGL | Draco Encode");
        return "webgl/dracoEncode";
    }
    
    
}
