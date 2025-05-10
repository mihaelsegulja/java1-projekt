/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.Repository;
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
public class SqlRepository implements Repository {
    
    // User properties
    private static final String ID_USER = "Id";
    private static final String USERNAME = "Username";
    private static final String PASSWORD_HASH = "PasswordHash";
    private static final String PASSWORD_SALT = "PasswordSalt";
    private static final String USERROLE_ID = "UserRoleId";
    
    // User CRUD
    private static final String CREATE_USER = "{ CALL spCreateUser (?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL spUpdateUser (?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL spDeleteUser (?) }";
    private static final String SELECT_USER = "{ CALL spSelectUser (?) }";
    private static final String SELECT_USERS = "{ CALL spSelectUsers }";
    
    // Author properties
    private static final String ID_AUTHOR = "Id";
    private static final String AUTHOR_NAME = "Name";
    private static final String AUTHOR_LINK = "Link";
    
    // Author CRUD
    private static final String CREATE_AUTHOR = "{ CALL spCreateAuthor (?,?,?) }";
    private static final String UPDATE_AUTHOR = "{ CALL spUpdateAuthor (?,?,?) }";
    private static final String DELETE_AUTHOR = "{ CALL spDeleteAuthor (?) }";
    private static final String SELECT_AUTHOR = "{ CALL spSelectAuthor (?) }";
    private static final String SELECT_AUTHORS = "{ CALL spSelectAuthors }";
    
    // Post properties
    private static final String ID_POST = "Id";
    private static final String POST_REDDIT_ID = "RedditId";
    private static final String POST_TITLE = "Title";
    private static final String POST_AUTHOR_ID = "AuthorId";
    private static final String POST_LINK = "Link";
    private static final String POST_THUMBNAIL_LINK = "ThumbnailLink";
    private static final String POST_CONTENT = "Content";
    private static final String POST_PUBLISHED_DATE = "PublishedDate";
    private static final String POST_UPDATED_DATE = "UpdatedDate";
    private static final String POST_SUBREDDIT_NAME = "SubredditName";
    
    // Post CRUD
    private static final String CREATE_POST = "{ CALL spCreatePost (?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_POST = "{ CALL spUpdatePost (?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_POST = "{ CALL spDeletePost (?) }";
    private static final String SELECT_POST = "{ CALL spSelectPost (?) }";
    private static final String SELECT_POSTS = "{ CALL spSelectPosts }";
    private static final String SELECT_POSTS_BY_SUBREDDIT = "{ CALL spSelectPostsBySubreddit (?) }";
    
    // Comment properties
    private static final String ID_COMMENT = "Id";
    private static final String COMMENT_REDDIT_ID = "RedditId";
    private static final String COMMENT_TITLE = "Title";
    private static final String COMMENT_AUTHOR_ID = "AuthorId";
    private static final String COMMENT_LINK = "Link";
    private static final String COMMENT_CONTENT = "Content";
    private static final String COMMENT_UPDATED_DATE = "UpdatedDate";
    private static final String COMMENT_SUBREDDIT_NAME = "SubredditName";
    
    // Comment CRUD
    private static final String CREATE_COMMENT = "{ CALL spCreateComment (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_COMMENT = "{ CALL spUpdateComment (?,?,?,?,?,?,?,?) }";
    private static final String DELETE_COMMENT = "{ CALL spDeleteComment (?) }";
    private static final String SELECT_COMMENT = "{ CALL spSelectComment (?) }";
    private static final String SELECT_COMMENTS = "{ CALL spSelectComments }";
    private static final String SELECT_COMMENTS_BY_POST = "{ CALL spSelectCommentsByPost (?) }";

    
    @Override
    public int createUser(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateUser(int id, User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<User> selectUsers() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int createAuthor(Author author) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void createAuthors(List<Author> authors) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateAuthor(int id, Author author) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteAuthor(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Author> selectAuthor(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Author> selectAuthors() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int createPost(Post post) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void createPosts(List<Post> posts) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updatePosts(int id, Post post) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deletePost(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Post> selectPost(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Post> selectPosts() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int createComment(Comment comment) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void createComments(List<Comment> comments) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateComment(int id, Comment comment) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteComment(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Comment> selectComment(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Comment> selectComments() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
