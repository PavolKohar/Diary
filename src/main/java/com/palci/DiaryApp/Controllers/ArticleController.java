package com.palci.DiaryApp.Controllers;


import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.Models.Services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    public String renderDashBoard(Model model){
        ArticleDTO lastEntry = articleService.findLastEntry();
        model.addAttribute("lastEntry",lastEntry);
        return "pages/article/dashboard";
    }


    @GetMapping("/create")
    public String renderCreateForm(@ModelAttribute ArticleDTO articleDTO){
        return "pages/article/form";
    }

    @PostMapping("/create")
    public String createEntry(@Valid @ModelAttribute ArticleDTO articleDTO, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return renderCreateForm(articleDTO);
        }

        articleService.createArticle(articleDTO);
        redirectAttributes.addFlashAttribute("success","Article created");

        return "redirect:/article"; // TODO change to redirect


    }



}
