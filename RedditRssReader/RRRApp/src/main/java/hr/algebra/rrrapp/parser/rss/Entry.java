/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.parser.rss;

import hr.algebra.dao.model.Author;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author miki
 */
public class Entry {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private int id;
    private String redditId;
    private String title;
    private Author author;
    private String link;
    private String thumbnailLink;
    private String content;
    private LocalDateTime publishedDate;
    private LocalDateTime updatedDate;
    private String subredditName;

    public Entry() {
    }

    public Entry(int id, String redditId, String title, Author author, String link, String thumbnailLink, String content, LocalDateTime publishedDate, LocalDateTime updatedDate, String subredditName) {
        this.id = id;
        this.redditId = redditId;
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnailLink = thumbnailLink;
        this.content = content;
        this.publishedDate = publishedDate;
        this.updatedDate = updatedDate;
        this.subredditName = subredditName;
    }

    public Entry(String redditId, String title, Author author, String link, String thumbnailLink, String content, LocalDateTime publishedDate, LocalDateTime updatedDate, String subredditName) {
        this.redditId = redditId;
        this.title = title;
        this.author = author;
        this.link = link;
        this.thumbnailLink = thumbnailLink;
        this.content = content;
        this.publishedDate = publishedDate;
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

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
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
        final Entry other = (Entry) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.redditId, other.redditId);
    }
}
