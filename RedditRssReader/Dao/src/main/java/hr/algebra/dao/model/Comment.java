/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author miki
 */
public final class Comment {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    
    private int id;
    private String redditId;
    private String title;
    private Author author;
    private String link;
    private String content;
    private OffsetDateTime updatedDate;
    private String subredditName;

    public Comment() { }

    public Comment(int id, String redditId, String title, Author author, String link, String content, OffsetDateTime updatedDate, String subredditName) {
        this.id = id;
        this.redditId = redditId;
        this.title = title;
        this.author = author;
        this.link = link;
        this.content = content;
        this.updatedDate = updatedDate;
        this.subredditName = subredditName;
    }
    
    public Comment(String redditId, String title, Author author, String link, String content, OffsetDateTime updatedDate, String subredditName) {
        this.redditId = redditId;
        this.title = title;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public OffsetDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(OffsetDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", redditId=" + redditId + ", title=" + title + ", author=" + author + ", link=" + link + ", content=" + content + ", updatedDate=" + updatedDate + ", subredditName=" + subredditName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.redditId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comment other = (Comment) obj;
        return this.id == other.id;
    }
}
