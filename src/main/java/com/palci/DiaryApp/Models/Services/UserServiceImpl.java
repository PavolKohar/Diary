package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.UserDTO;
import com.palci.DiaryApp.Models.Exceptions.DuplicateEmailException;
import com.palci.DiaryApp.Models.Exceptions.PasswordDoNotEqualException;
import com.palci.DiaryApp.data.Entities.UserEntity;
import com.palci.DiaryApp.data.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void create(UserDTO userDTO, boolean isAdmin) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            throw new PasswordDoNotEqualException();
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(userDTO.getEmail());
        userEntity.setUsername(userEntity.getUsername());
        userEntity.setAdmin(isAdmin);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));


        try {
            userRepository.save(userEntity);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEmailException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Email " + username + " not found"));
    }
}
