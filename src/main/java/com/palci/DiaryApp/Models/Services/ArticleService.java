package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.data.Entities.UserEntity;

import java.util.List;

public interface ArticleService {

    void createArticle(ArticleDTO articleDTO,String userEmail);

    ArticleDTO findLastEntry(UserEntity user);

    List<ArticleDTO> getTopArticles(UserEntity user);

    List<ArticleDTO> getAllArticles(UserEntity user);

    List<ArticleDTO> getTopMoodEntries(UserEntity user);

    List<ArticleDTO> getMidMoodEntries(UserEntity user);

    List<ArticleDTO> getLowMoodEntries(UserEntity user);

    String getDaysFromEntry(ArticleDTO articleDTO);

    ArticleDTO getById(long articleId);

    void edit (ArticleDTO articleDTO);


}
