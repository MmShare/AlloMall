package com.example.allomall.repostitory;

import com.example.allomall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepostitory extends JpaRepository<User,Long> {

    List<User> findUsersByAccountAndPwd(String account,String pwd);
}
