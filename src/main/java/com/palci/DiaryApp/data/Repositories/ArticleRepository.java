package com.palci.DiaryApp.data.Repositories;

import com.palci.DiaryApp.data.Entities.ArticleEntity;
import com.palci.DiaryApp.data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
    ArticleEntity findTopByOrderByDateDescTimeDesc();
    ArticleEntity findTopByOwnerOrderByDateDescTimeDesc(UserEntity owner);
    List<ArticleEntity> findAllByOwner(UserEntity owner);
}
