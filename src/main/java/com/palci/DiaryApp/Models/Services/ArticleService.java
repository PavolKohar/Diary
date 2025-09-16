package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void createArticle(ArticleDTO articleDTO,String userEmail);

    ArticleDTO findLastEntry();

    List<ArticleDTO> getTopArticles();

    List<ArticleDTO> getAllArticles();

    List<ArticleDTO> getTopMoodEntries();

    List<ArticleDTO> getMidMoodEntries();

    List<ArticleDTO> getLowMoodEntries();

    String getDaysFromEntry(ArticleDTO articleDTO);

    ArticleDTO getById(long articleId);

    void edit (ArticleDTO articleDTO);


}
