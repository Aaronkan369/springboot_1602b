package hello.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface StuDao extends CrudRepository<Stu, Long> ,JpaRepository<Stu, Long>{
	public List<Stu> findByNameLike(String name);//select * from stu where name like "?name?"

	public List<Stu> findByNameLikeAndAge(String string, String age);
	
	@Query(value="from Stu where name=?1 and age=?2")//面向对象
	public List<Stu> list1(String name, String age);
	
	@Query(value="select * from student where name=?1 and age=?2",nativeQuery=true)//面向数据库
	public List<Stu> list2(String name, String age);
	
	@Query(value = "select * from student where name=?1 ",
		    countQuery = "select count(*) from student where name=?1 ",
		    nativeQuery = true)
	Page<Stu> page1(String name, Pageable pageable);
	
	
	List<Stu> findAll(Specification<Stu> spec);

	Page<Stu> findAll(Specification<Stu> specification, Pageable pa);

	List<Stu> findByNameAndAge(String name, String age);
	
}
