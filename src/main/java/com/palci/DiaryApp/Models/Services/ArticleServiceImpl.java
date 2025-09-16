package com.palci.DiaryApp.Models.Services;

import com.palci.DiaryApp.Models.ArticleMapper;
import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.Models.Exceptions.ArticleNotFoundException;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import com.palci.DiaryApp.data.Entities.UserEntity;
import com.palci.DiaryApp.data.Repositories.ArticleRepository;
import com.palci.DiaryApp.data.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void createArticle(ArticleDTO articleDTO,String userEmail) {
        UserEntity owner = userRepository.findByEmail(userEmail).orElseThrow(()->new RuntimeException("User not found"));
        ArticleEntity entry = articleMapper.toEntity(articleDTO); // TODO - Figure out how to encrypt and decrypt articles with AESUtil.java
        entry.setOwner(owner);
        articleRepository.save(entry);
    }

    @Override
    public ArticleDTO findLastEntry() {
        ArticleEntity entity = articleRepository.findTopByOrderByDateDescTimeDesc();
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

    @Override
    public List<ArticleDTO> getMidMoodEntries() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(),false)
                .map(e->articleMapper.toDto(e))
                .filter(d->d.getMood()<7 && d.getMood()>4)
                .toList();
    }

    @Override
    public List<ArticleDTO> getLowMoodEntries() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(),false)
                .map(e->articleMapper.toDto(e))
                .filter(d->d.getMood()<=4)
                .toList();
    }

    @Override
    public String getDaysFromEntry(ArticleDTO articleDTO) {
        LocalDate today = LocalDate.now();

        LocalDate entryDate;
        try {
            entryDate  = articleDTO.getDate();
        }catch (NullPointerException e){
            entryDate = today;
        }

        int days = Period.between(entryDate,today).getDays();


        switch (days){
            case 0 -> {
                return "today";
            }
            case 1 -> {
                return days + " day ago";
            }
            default -> {
                return days + " days ago";
            }
        }
    }

    @Override
    public ArticleDTO getById(long articleId) {
        ArticleEntity fetchedArticle = getArticleOrThrow(articleId);
        return articleMapper.toDto(fetchedArticle);
    }

    @Override
    public void edit(ArticleDTO articleDTO) {
        ArticleEntity fetchedEntity = getArticleOrThrow(articleDTO.getArticleId());
        articleMapper.updateArticleEntity(articleDTO,fetchedEntity);
        articleRepository.save(fetchedEntity);
    }

    // Helping method
    private ArticleEntity getArticleOrThrow(long articleId){
        return articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
    }
}
