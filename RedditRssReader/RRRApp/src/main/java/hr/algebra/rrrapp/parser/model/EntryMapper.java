/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.rrrapp.parser.model;

import hr.algebra.dao.model.Comment;
import hr.algebra.dao.model.Post;
import java.util.List;

/**
 *
 * @author miki
 */
public class EntryMapper {
    public static List<Post> mapToPosts(List<Entry> entries) {
        return entries.stream()
            .map(ent -> new Post(
                    ent.getId(),
                    ent.getRedditId(),
                    ent.getTitle(),
                    ent.getAuthor(),
                    ent.getLink(),
                    ent.getThumbnailLink(),
                    ent.getContent(),
                    ent.getPublishedDate(),
                    ent.getUpdatedDate(),
                    ent.getSubredditName()
            )).toList();
    }

    public static List<Comment> mapToComments(List<Entry> entries) {
        return entries.stream()
            .map(ent -> new Comment(
                    ent.getId(),
                    ent.getRedditId(),
                    ent.getTitle(),
                    ent.getAuthor(),
                    ent.getLink(),
                    ent.getContent(),
                    ent.getUpdatedDate(),
                    ent.getSubredditName()
            )).toList();
    }
}
