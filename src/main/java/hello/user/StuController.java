package hello.user;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RequestMapping("stu")
@RestController
public class StuController {
	@Autowired
	private  StuDao stuDao;
    @RequestMapping("list")
    public Object stulist() {
    	Iterable<Stu> list = stuDao.findAll();
        return list;
    }
    @RequestMapping("listByName")
    public Object listByName(String name) {
    	List<Stu> list = stuDao.findByNameLike(name+"%");
        return list;
    }
    @RequestMapping("listBy")
    public Object listBy(String name,String age) {
    	List<Stu> list = stuDao.findByNameLikeAndAge(name+"%",age);
        return list;
    }
    
    @RequestMapping("list1")
    public Object list1(String name,String age) {
    	List<Stu> list = stuDao.list1(name, age);
        return list;
    }
    
    @RequestMapping("tolist1")
    public Object tolist1(String name,String age,Model model) {
    	List<Stu> list = stuDao.list1(name, age);
    	model.addAttribute("list",list);
        return new ModelAndView("user/tolist1");
    }
    
    @RequestMapping("list2")
    public Object list2(String name,String age) {
    	List<Stu> list = stuDao.list2(name, age);
        return list;
    }
    
    @RequestMapping("list3")
    public Object list3(String name,String age) {
    	
    	Specification<Stu> specification = new Specification<Stu>() {
			@Override
			public Predicate toPredicate(Root<Stu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>(); //所有的断言
                if(!StringUtils.isEmpty(name)){ //添加断言  ==null or ==""
                    Predicate namelike = cb.like(root.get("name").as(String.class),name+"%");
                    predicates.add(namelike);
                }
                if(!StringUtils.isEmpty(age)){ //添加断言  ==null or ==""
                    Predicate ageeq = cb.equal(root.get("age").as(String.class),age);
                    predicates.add(ageeq);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
			}
    		
    	};
		List<Stu> list = stuDao.findAll(specification);
        return list;
    }
    
    

    @RequestMapping("page3")
    public Object page3(String name,String age,@RequestParam(defaultValue="0") int page ,@RequestParam(defaultValue="10") int size) {
    	//规格
    	Specification<Stu> specification = new Specification<Stu>() {
			@Override
			public Predicate toPredicate(Root<Stu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>(); //所有的断言
                if(!StringUtils.isEmpty(name)){ //添加断言  ==null or ==""
                    Predicate namelike = cb.like(root.get("name").as(String.class),name+"%");
                    predicates.add(namelike);
                }
                if(!StringUtils.isEmpty(age)){ //添加断言  ==null or ==""
                    Predicate ageeq = cb.equal(root.get("age").as(String.class),age);
                    predicates.add(ageeq);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
			}
    		
    	};
    	Pageable pa=PageRequest.of(page, size);
		Page<Stu> pages = stuDao.findAll(specification,pa);
        return pages;
    }
    
    @RequestMapping("page1")
    public Object page1(String name,int page,int size) {
    	Pageable pageable =PageRequest.of(page, size);
		Page<Stu> pages = stuDao.page1(name, pageable);
        return pages;
    }
    
    @Autowired
    private EntityManager entityManager;
    
    @RequestMapping("page4")
    public Object page4(String name,String age,int page,int size) {
    	//无论是分页的数据还是差分页条数 where条件一样
    	String countsql="select count(*) from student   where 1=1 ";
    	String querysql="select * from student   where 1=1 ";
    	String wheresql="";
    	Map<String,Object> params = new HashMap<>();//条件的值
    	if (!StringUtils.isEmpty(name)) {
    		wheresql+=" and name =:name ";
    		params.put("name",name);
		}
    	if (!StringUtils.isEmpty(age)) {
    		wheresql+=" age =:age ";
    		params.put("age",age);
		}
    	
	   Query countQuery = this.entityManager.createNativeQuery( countsql+wheresql);
       this.setParameters(countQuery,params);
       int count =  ((BigInteger) countQuery.getSingleResult()).intValue();//总数
       
       
       Query query = this.entityManager.createNativeQuery(querysql+wheresql,Stu.class);
       this.setParameters(query,params);
       
       PageRequest pa = PageRequest.of(page, size);
       //设置分页起始位置
       query.setFirstResult((int) pa.getOffset());
       query.setMaxResults(pa.getPageSize());
       //查询
       List<Stu> list = query.getResultList();
       //封装
       Page<Stu> pagex = new PageImpl<Stu>(list, pa, count);
       return pagex;
       
    	
    }
    
    private void setParameters(Query query,Map<String,Object> params){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
    }
    
    @RequestMapping("add")
    public Object add(Stu stu) {
    	stuDao.save(stu);
        return stu;
    }
    
}
