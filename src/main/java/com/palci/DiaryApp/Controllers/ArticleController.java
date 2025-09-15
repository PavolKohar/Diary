package com.palci.DiaryApp.Controllers;


import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.Models.Services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping
    public String renderDashBoard(Model model,RedirectAttributes redirectAttributes){
        try {
            ArticleDTO lastEntry = articleService.findLastEntry();
            model.addAttribute("lastEntry",lastEntry);
            List<ArticleDTO> topEntries = articleService.getTopArticles();
            model.addAttribute("topEntries",topEntries);
            List<ArticleDTO> allEntries = articleService.getAllArticles();
            model.addAttribute("allEntries",allEntries);
            List<ArticleDTO> topMoodEntries = articleService.getTopMoodEntries();
            model.addAttribute("moodTop",topMoodEntries);
            return "pages/article/dashboard";
        }catch (SpelEvaluationException e ){
            redirectAttributes.addFlashAttribute("error","Not record yet");
            return "redirect:/";

        }
    }

    @GetMapping("/{filter}")
    public String renderDashBoardWithFilterMood(@PathVariable String filter, Model model,RedirectAttributes redirectAttributes){
        try {
            ArticleDTO lastEntry = articleService.findLastEntry();
            model.addAttribute("lastEntry",lastEntry);
            List<ArticleDTO> topEntries = articleService.getTopArticles();
            model.addAttribute("topEntries",topEntries);
            List<ArticleDTO> allEntries = articleService.getAllArticles();
            model.addAttribute("allEntries",allEntries);

            List<ArticleDTO> byMood;
            switch (filter){
                case "top-mood" -> byMood = articleService.getTopMoodEntries();
                case "mid-mood" -> byMood = articleService.getMidMoodEntries();
                case "low-mood" -> byMood = articleService.getLowMoodEntries();
                default -> byMood = articleService.getAllArticles();
            }

            model.addAttribute("moodTop",byMood);

            return "pages/article/dashboard";

        }catch (SpelEvaluationException e){
            redirectAttributes.addFlashAttribute("error","Not record yet");
            return "redirect:/";
        }

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
