package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.ArticleMapper;
import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import com.palci.DiaryApp.data.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void createArticle(ArticleDTO articleDTO) {
        ArticleEntity entry = articleMapper.toEntity(articleDTO);
        articleRepository.save(entry);

    }
}
