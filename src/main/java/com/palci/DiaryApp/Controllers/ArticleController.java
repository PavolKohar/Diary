package com.palci.DiaryApp.Controllers;


import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/article")
public class ArticleController {


    @GetMapping("/create")
    public String renderCreateForm(@ModelAttribute ArticleDTO articleDTO){
        return "pages/article/form";
    }

    @PostMapping("/create")
    public String createEntry(@Valid @ModelAttribute ArticleDTO articleDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return renderCreateForm(articleDTO);
        }

        // TODO create method to create article

        return "pages/article/dashboard"; // TODO create dashboard.html


    }



}
