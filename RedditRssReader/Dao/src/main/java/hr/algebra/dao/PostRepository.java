/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dao;

import hr.algebra.dao.model.Post;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author miki
 */
public interface PostRepository {
    int createPost(Post post) throws Exception;
    void createPosts(List<Post> posts) throws Exception;
    void updatePost(int id, Post post) throws Exception;
    void deletePost(int id) throws Exception;
    Optional<Post> selectPost(int id) throws Exception;
    List<Post> selectPosts() throws Exception;
    List<Post> selectPostsBySubreddit(String subredditName) throws Exception;
}
