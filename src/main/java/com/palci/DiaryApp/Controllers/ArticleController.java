package com.palci.DiaryApp.Controllers;


import com.palci.DiaryApp.Models.ArticleMapper;
import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.Models.Exceptions.ArticleNotFoundException;
import com.palci.DiaryApp.Models.Services.ArticleService;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import com.palci.DiaryApp.data.Entities.UserEntity;
import com.palci.DiaryApp.data.Repositories.ArticleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired ArticleRepository articleRepository;

    @GetMapping
    public String renderDashBoard(Model model,RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserEntity user){
        try {
            ArticleDTO lastEntry = articleService.findLastEntry(user);
            model.addAttribute("lastEntry",lastEntry);
            List<ArticleDTO> topEntries = articleService.getTopArticles(user);
            model.addAttribute("topEntries",topEntries);
            List<ArticleDTO> allEntries = articleService.getAllArticles(user);
            model.addAttribute("allEntries",allEntries);
            List<ArticleDTO> topMoodEntries = articleService.getTopMoodEntries(user);
            model.addAttribute("moodTop",topMoodEntries);
            String daysAgoMessage = articleService.getDaysFromEntry(lastEntry);
            model.addAttribute("daysMessage",daysAgoMessage);
            return "pages/article/dashboard";
        }catch (SpelEvaluationException e ){
            redirectAttributes.addFlashAttribute("error","Not record yet");
            return "redirect:/";

        }
    }

    @GetMapping("/{filter}")
    public String renderDashBoardWithFilterMood(@PathVariable String filter, Model model,RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserEntity user){
        try {
            ArticleDTO lastEntry = articleService.findLastEntry(user);
            model.addAttribute("lastEntry",lastEntry);
            List<ArticleDTO> topEntries = articleService.getTopArticles(user);
            model.addAttribute("topEntries",topEntries);
            List<ArticleDTO> allEntries = articleService.getAllArticles(user);
            model.addAttribute("allEntries",allEntries);
            String daysAgoMessage = articleService.getDaysFromEntry(lastEntry);
            model.addAttribute("daysMessage",daysAgoMessage);

            List<ArticleDTO> byMood;
            switch (filter){
                case "top-mood" -> byMood = articleService.getTopMoodEntries(user);
                case "mid-mood" -> byMood = articleService.getMidMoodEntries(user);
                case "low-mood" -> byMood = articleService.getLowMoodEntries(user);
                default -> byMood = articleService.getAllArticles(user);
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
    public String createEntry(@Valid @ModelAttribute ArticleDTO articleDTO, BindingResult result, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserEntity user){
        String email = user.getEmail();

        if (result.hasErrors()){
            return renderCreateForm(articleDTO);
        }


        articleService.createArticle(articleDTO,email);
        redirectAttributes.addFlashAttribute("success","Article created");

        return "redirect:/article"; // TODO change to redirect


    }

    @GetMapping("/top-records")
    public String renderTopRecords(Model model,@AuthenticationPrincipal UserEntity user){
        List<ArticleDTO> topArticles = articleService.getTopArticles(user);
        model.addAttribute("records",topArticles);
        return "pages/article/topRecords";
    }

    @GetMapping("/detail/{articleId}")
    public String renderDetail(@PathVariable long articleId,Model model,@AuthenticationPrincipal UserEntity user){
        ArticleEntity entity = articleRepository.findById(articleId).orElseThrow(); // TODO - finish exception

        if (entity.getOwner().getUserId() != user.getUserId()){
            throw new AccessDeniedException("Not allowed");
        }

        try {
            ArticleDTO article = articleService.getById(articleId);
            model.addAttribute("article",article);
            String daysAgoMessage = articleService.getDaysFromEntry(article);
            model.addAttribute("daysMessage",daysAgoMessage);
            return "pages/article/detail";
        }catch (ArticleNotFoundException e ){
            return "redirect:/article";
        }

    }

    @GetMapping("edit/{articleId}")
    public String renderEditForm(@PathVariable long articleId,@ModelAttribute ArticleDTO articleDTO,Model model,@AuthenticationPrincipal UserEntity user){
        ArticleEntity entity = articleRepository.findById(articleId).orElseThrow();

        if (entity.getOwner().getUserId() != user.getUserId()){
            throw new AccessDeniedException("Not allowed");
        }

        try {
           ArticleDTO fetchedArticle = articleService.getById(articleId);
           LocalDate originDate = fetchedArticle.getDate();
           model.addAttribute("originDate",originDate);
           articleMapper.updateArticleDTO(fetchedArticle,articleDTO);
           return "pages/article/edit";
       }catch (ArticleNotFoundException e){
           return "redirect:/article";
       }
    }

    @PostMapping("edit/{articleId}")
    public String updateArticle(@PathVariable long articleId,@Valid @ModelAttribute ArticleDTO articleDTO,BindingResult result,RedirectAttributes redirectAttributes,Model model,@AuthenticationPrincipal UserEntity user){
        ArticleEntity entity = articleRepository.findById(articleId).orElseThrow();

        if (entity.getOwner().getUserId() != user.getUserId()){
            throw new AccessDeniedException("Not Allowed");
        }


        if (result.hasErrors()){
            return renderEditForm(articleId,articleDTO,model,user);
        }

        articleDTO.setArticleId(articleId);
        articleService.edit(articleDTO);
        redirectAttributes.addFlashAttribute("success","Article edited");

        return "redirect:/article";
    }

    @GetMapping("remove/{articleId}")
    public String removeArticle(@PathVariable long articleId,@AuthenticationPrincipal UserEntity user){
        ArticleEntity entity = articleRepository.findById(articleId).orElseThrow();
        if (entity.getOwner().getUserId() != user.getUserId()){
            throw new AccessDeniedException("Not allowed");
        }

        articleRepository.deleteById(articleId); // TODO edit this method to throw exception if entry does not exist

        return "redirect:/article";
    }



}
