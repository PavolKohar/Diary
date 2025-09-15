package com.palci.DiaryApp.Models;

import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleEntity toEntity(ArticleDTO source);

    ArticleDTO toDto(ArticleEntity source);
}
