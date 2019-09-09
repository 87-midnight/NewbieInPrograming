package com.lcg.sample.repository;

import com.lcg.sample.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query(value = "select id,title_,content_,create_by,create_time from article order by create_time desc",nativeQuery = true)
    List<Article> query();
}
