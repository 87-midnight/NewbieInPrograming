package com.lcg.sample.service;

import com.lcg.sample.entity.Article;
import com.lcg.sample.repository.ArticleRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linchuangang
 * @create 2019-10-25 14:34
 **/
@Service
@Data
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void save(Article article){
        this.articleRepository.save(article);
    }
    public List<Article> queryAll(){
        return this.articleRepository.findAll();
    }
}
