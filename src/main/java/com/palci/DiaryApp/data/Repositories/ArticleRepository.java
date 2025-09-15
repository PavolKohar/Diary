package com.palci.DiaryApp.data.Repositories;

import com.palci.DiaryApp.data.Entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
}
