package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.ArticleMapper;
import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.Models.Exceptions.ArticleNotFoundException;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import com.palci.DiaryApp.data.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

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

    @Override
    public ArticleDTO findLastEntry() {
        ArticleEntity entity = articleRepository.findTopByOrderByDateDesc();
        return articleMapper.toDto(entity);
    }

    @Override
    public List<ArticleDTO> getTopArticles() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(),false)
                .map(e->articleMapper.toDto(e))
                .filter(ArticleDTO::isTop)
                .toList();
    }

    @Override
    public List<ArticleDTO> getAllArticles() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(),false)
                .map(e->articleMapper.toDto(e))
                .toList();
    }

    @Override
    public List<ArticleDTO> getTopMoodEntries() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(),false)
                .map(e->articleMapper.toDto(e))
                .filter(d->d.getMood()>=7)
                .toList();
    }

    // Helping method
    private ArticleEntity getArticleOrThrow(long articleId){
        return articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    }
}
