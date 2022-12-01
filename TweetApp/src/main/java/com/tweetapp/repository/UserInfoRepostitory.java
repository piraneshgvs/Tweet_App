package com.tweetapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.UserTable;

@Repository
public interface UserInfoRepostitory extends CrudRepository<UserTable, String>{
	
	@Query(value="select user_id from user_info where user_id= :userId",nativeQuery =true)
	public String notInUserId(@Param("userId") String userId);
	
	@Query(value="select email_id from user_info where email_id= :emailId",nativeQuery = true)
	public String notInEmailId(@Param("emailId") String emailId);
	
	@Query(value="select * from user_info where user_id= :userId",nativeQuery = true)
	public UserTable getByUserId(@Param("userId") String userId);
	
	@Query(value="select user_id from user_info",nativeQuery=true)
	public List<String> findAllUser();
	
	@Query(value="select user_id from user_info where user_id like %:userId%",nativeQuery=true)
	public List<String> searchUserId(@Param("userId") String userId);
	
	@Modifying
	@Transactional
	@Query(value="update user_info set password= :newPassword where user_id= :userId",nativeQuery=true)
	public void updatePassword(@Param("newPassword")String newPassword, @Param("userId")String userId);
	
	@Query(value="select * from user_info where email_id= :emailId",nativeQuery=true)
    public UserTable getByEamilId(@Param("emailId")String emailId);
	
	@Modifying
	@Transactional
	@Query(value="update user_info set last_logout_date= :timestamp where user_id= :userId",nativeQuery=true)
	public void updateLogOut(@Param("timestamp")LocalDateTime timestamp, @Param("userId") String userId);
	
	@Modifying
	@Transactional
	@Query(value="update user_info set last_login_date= :timestamp where user_id= :userId",nativeQuery=true)
	public void updateLogIn(@Param("timestamp")LocalDateTime timestamp, @Param("userId") String userId);
    

}
