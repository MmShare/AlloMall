package com.example.allomall.repostitory;

import com.example.allomall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostitory extends JpaRepository<User,Long> {
}
