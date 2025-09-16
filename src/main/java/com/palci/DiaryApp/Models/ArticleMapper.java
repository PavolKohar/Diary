package com.palci.DiaryApp.Models;

import com.palci.DiaryApp.Models.DTO.ArticleDTO;
import com.palci.DiaryApp.data.Entities.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleEntity toEntity(ArticleDTO source);

    ArticleDTO toDto(ArticleEntity source);


    void updateArticleDTO(ArticleDTO source, @MappingTarget ArticleDTO target);

    void updateArticleEntity(ArticleDTO source, @MappingTarget ArticleEntity target);
}
