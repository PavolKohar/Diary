package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.UserDTO;

public interface UserService {

    void create(UserDTO userDTO,boolean isAdmin);
}
