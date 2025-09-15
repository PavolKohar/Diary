package com.palci.DiaryApp.Controllers;


import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {


    @GetMapping("/create")
    public String renderCreateForm(@ModelAttribute ArticleDTO articleDTO){
        return "pages/article/form";
    }



}
