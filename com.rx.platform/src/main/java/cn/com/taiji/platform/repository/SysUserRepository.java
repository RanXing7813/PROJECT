package cn.com.taiji.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.SysUser;

@Transactional
public interface SysUserRepository extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {

	@Query("select u from SysUser u where u.ustate=1 order by u.uindex")
	List findAllUsers();
	
	@Query("select u from SysUser u where u.ustate=1 and u.loginname=:loginname and u.password=:password ")
	SysUser login(@Param("loginname") String loginname, @Param("password") String password);
	
	@Query("select u from SysUser u where u.ustate=1 and u.loginname=:loginname ")
	SysUser findUserByLoginName(@Param("loginname") String loginname);

}
