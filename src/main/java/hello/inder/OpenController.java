package hello.inder;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hello.core.SessionUtil;
import hello.user.Stu;
@RequestMapping("open")
@RestController
public class OpenController {
    
    @RequestMapping("login")
    public ModelAndView index() {
        return new ModelAndView("login");
    }
    
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	req.getSession().invalidate();
//    	resp.sendRedirect("/open/login");
    	return new ModelAndView("redirect:/open/login");
    }
    
    
    @Autowired
    private EntityManager entityManager;
    
    
    @RequestMapping("dologin")
    public Object dologin(Stu stu,HttpServletRequest req,HttpServletResponse resp) throws IOException {
    	
    	 Query query = this.entityManager.createNativeQuery("select * from student "
    	 		+ "where name='"+stu.getName()+"' and age='"+stu.getAge()+"'",Stu.class);
    	 List<Stu> stu1 = query.getResultList();
    	 
    	if (stu1!=null&&stu1.size()>0) {
    		SessionUtil.setUser(req.getSession(), stu1.get(0));
    		resp.sendRedirect("/stu/topage1");
    		return null;
		}
    	resp.sendRedirect("/open/login");
    	return null;
    }
    
    
    
}
