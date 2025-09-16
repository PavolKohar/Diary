package com.palci.DiaryApp.Controllers;

import com.palci.DiaryApp.Models.DTO.UserDTO;
import com.palci.DiaryApp.Models.Exceptions.DuplicateEmailException;
import com.palci.DiaryApp.Models.Exceptions.PasswordDoNotEqualException;
import com.palci.DiaryApp.Models.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    UserService userService;


    @GetMapping("/")
    public String renderIndex(){
        return "pages/home/index";
    }

    @GetMapping("/register")
    public String renderRegister(@ModelAttribute UserDTO userDTO){
        return "pages/home/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return renderRegister(userDTO);
        }

        try {
            userService.create(userDTO,false);
        }catch (DuplicateEmailException e){
            result.rejectValue("email","error","Email already in use");
            return "pages/home/register";
        }catch (PasswordDoNotEqualException e){
            result.rejectValue("password","error","Passwords do not match");
            result.rejectValue("confirmPassword","error","Passwords do not match");
            return "pages/home/register";
        }

        redirectAttributes.addFlashAttribute("success","User was register");

        return "pages/home/login";

    }

    @GetMapping("/login")
    public String renderLogin(){
        return "pages/home/login";
    }
}
