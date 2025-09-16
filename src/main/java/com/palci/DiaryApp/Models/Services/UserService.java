package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void create(UserDTO userDTO,boolean isAdmin);
}
