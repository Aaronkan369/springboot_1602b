package hello.inder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    
    @RequestMapping("/")
    public String index() {
        return "index2";
    }
    @RequestMapping("/list")
    public Map list() {
    	Map m=new HashMap() 
    	{{
    		put("name", "zs");
    		put("age", 12);
    	}};
    	
    	ArrayList a=new ArrayList() {{add("1");add("2");}};
    	
        return m;
    }
    
    @RequestMapping(value="/user/{uid}",method=RequestMethod.GET)
    public ModelAndView getUser(@PathVariable("uid") Integer uid,Model model) {
    	Map m=new HashMap() 
    	{{
    		put("id", uid);
    		put("name", "zs");
    		put("age", 12);
    	}};
    	model.addAttribute("m", m);
        return new ModelAndView("test");
    }
    
    @RequestMapping(value="/user/{uid}",method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable int uid,Model model) {
        return "true";
    }
    
    
    
}
