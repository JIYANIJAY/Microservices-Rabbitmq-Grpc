package com.inexture.service;


import com.gbs.common.dto.UserDTO;

import java.util.List;

public interface UserServices {

    UserDTO saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
}
