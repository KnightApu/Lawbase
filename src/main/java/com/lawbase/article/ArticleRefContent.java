package com.lawbase.article;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.book.content.Content;

public class ArticleRefContent implements Content {

    @DBRef
    Article article;
    
    
    public ArticleRefContent() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public ArticleRefContent( Article article ) {
        super();
        this.article = article;
    }


    public Article getArticleBook() {
    
        return article;
    }

    
    public void setArticleBook( Article article ) {
    
        this.article = article;
    }

    @Override
    public String getTitle() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isURL() {
        
        return true;
        
    }
    @Override
    public String getURL() {

        // TODO Auto-generated method stub
        return null;
    }

}
