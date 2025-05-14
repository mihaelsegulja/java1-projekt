/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao;

import hr.algebra.dao.model.Author;
import hr.algebra.dao.model.Comment;
import hr.algebra.dao.model.Post;
import hr.algebra.dao.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author miki
 */
public interface Repository {
    
    // User CRUD
    int createUser(User user) throws Exception;
    void updateUser(int id, User user) throws Exception;
    void deleteUser(int id) throws Exception;
    Optional<User> selectUser(int id) throws Exception;
    List<User> selectUsers() throws Exception; // might not need it
    
    // Author CRUD
    int createAuthor(Author author) throws Exception;
    void createAuthors(List<Author> authors) throws Exception;
    void updateAuthor(int id, Author author) throws Exception;
    void deleteAuthor(int id) throws Exception;
    Optional<Author> selectAuthor(int id) throws Exception;
    List<Author> selectAuthors() throws Exception;
    
    // Post CRUD
    int createPost(Post post) throws Exception;
    void createPosts(List<Post> posts) throws Exception;
    void updatePosts(int id, Post post) throws Exception;
    void deletePost(int id) throws Exception;
    Optional<Post> selectPost(int id) throws Exception;
    List<Post> selectPosts() throws Exception;
    
    // Comment CRUD
    int createComment(Comment comment) throws Exception;
    void createComments(List<Comment> comments) throws Exception;
    void updateComment(int id, Comment comment) throws Exception;
    void deleteComment(int id) throws Exception;
    Optional<Comment> selectComment(int id) throws Exception;
    List<Comment> selectComments() throws Exception;
    
    // Additional helpers
    List<Post> selectPostsBySubreddit(String subredditName) throws Exception;
    List<Comment> selectCommentsbyPost(int postId) throws Exception;
    void deleteAll() throws Exception;
}
