package org.example.entity;

import java.util.Date;

public class ArticlePreview {
    private int id;
    private String mainHeader;
    private String subHeader;
    private String text;
    private String link;
    private Date date;

    public ArticlePreview() {}

    public ArticlePreview(int id, String mainHeader, String subHeader, String text, String link, Date date) {
        this.id = id;
        this.mainHeader = mainHeader;
        this.subHeader = subHeader;
        this.text = text;
        this.link = link;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMainHeader() {
        return mainHeader;
    }

    public void setMainHeader(String mainHeader) {
        this.mainHeader = mainHeader;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ArticlePreview{" +
                "id=" + id +
                ", date=" + date +
                ", mainHeader='" + mainHeader + '\'' +
                ", subHeader='" + subHeader + '\'' +
                ", link='" + link + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

