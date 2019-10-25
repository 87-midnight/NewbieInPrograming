package com.lcg.sample;

import com.alibaba.fastjson.JSON;
import com.lcg.sample.entity.Article;
import com.lcg.sample.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.midnight.generator.CodeGenerator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
@EnableJpaAuditing
public class JpaApplication implements ApplicationRunner {

    private final ArticleService articleService;
    private final CodeGenerator codeGenerator;
    public static void main(String...args){
        new SpringApplicationBuilder(JpaApplication.class).web(WebApplicationType.NONE).run(args);
    }

    public void run(ApplicationArguments args) throws Exception {
        //view more info about code-generator-spring-boot-starter on github
        codeGenerator.print();
        Article article = Article.builder().build();
        article.setContent("hello");
        article.setTitle("1234");
        this.articleService.save(article);
        article = Article.builder().content("well,well,well").title("what's wrong").build();
        this.articleService.save(article);
        List list = this.articleService.queryAll();
        log.info("query list:{}", JSON.toJSONString(list));
    }
}
