package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void createArticle(ArticleDTO articleDTO);

    ArticleDTO findLastEntry();

    List<ArticleDTO> getTopArticles();
}
