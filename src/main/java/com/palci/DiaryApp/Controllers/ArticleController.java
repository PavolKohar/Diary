package com.palci.DiaryApp.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {


    @GetMapping("/create")
    public String renderCreateForm(){
        return "pages/article/form";
    }


}
