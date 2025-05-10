/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author miki
 */
public final class Comment {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private int id;
    private String redditId;
    private String title;
    private int authorId;
    private String link;
    private String content;
    private LocalDateTime updatedDate;
    private String subredditName;

    public Comment() { }

    public Comment(int id, String redditId, String title, int authorId, String link, String content, LocalDateTime updatedDate, String subredditName) {
        this.id = id;
        this.redditId = redditId;
        this.title = title;
        this.authorId = authorId;
        this.link = link;
        this.content = content;
        this.updatedDate = updatedDate;
        this.subredditName = subredditName;
    }

    public Comment(String redditId, String title, int authorId, String link, String content, LocalDateTime updatedDate, String subredditName) {
        this.redditId = redditId;
        this.title = title;
        this.authorId = authorId;
        this.link = link;
        this.content = content;
        this.updatedDate = updatedDate;
        this.subredditName = subredditName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRedditId() {
        return redditId;
    }

    public void setRedditId(String redditId) {
        this.redditId = redditId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }
}
