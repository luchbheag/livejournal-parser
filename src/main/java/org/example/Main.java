package org.example;

import org.example.entity.ArticlePreview;
import org.example.parser.LJParser;

public class Main {
    public static void main(String[] args) {
        String url = "https://vykhochetepesen.livejournal.com/";
        LJParser parser = new LJParser();
        for (ArticlePreview articlePreview : parser.getAllLaterArticlePreviewsSinceId(url)) {
            System.out.println(articlePreview);
        }
    }
}