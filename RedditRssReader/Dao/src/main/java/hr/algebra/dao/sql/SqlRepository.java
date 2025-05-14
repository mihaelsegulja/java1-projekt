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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author miki
 */
public class SqlRepository implements Repository {
    
    // User
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
    
    // Author
    private static final String ID_AUTHOR = "Id";
    private static final String AUTHOR_NAME = "Name";
    private static final String AUTHOR_LINK = "Link";
    
    // Author CRUD
    private static final String CREATE_AUTHOR = "{ CALL spCreateAuthor (?,?,?) }";
    private static final String UPDATE_AUTHOR = "{ CALL spUpdateAuthor (?,?,?) }";
    private static final String DELETE_AUTHOR = "{ CALL spDeleteAuthor (?) }";
    private static final String SELECT_AUTHOR = "{ CALL spSelectAuthor (?) }";
    private static final String SELECT_AUTHORS = "{ CALL spSelectAuthors }";
    
    // Post
    private static final String ID_POST = "Id";
    private static final String POST_REDDIT_ID = "RedditId";
    private static final String POST_TITLE = "Title";
    private static final String POST_AUTHOR_ID = "AuthorId";
    private static final String POST_AUTHOR_NAME = "AuthorName";
    private static final String POST_AUTHOR_LINK = "AuthorLink";
    private static final String POST_LINK = "Link";
    private static final String POST_THUMBNAIL_LINK = "ThumbnailLink";
    private static final String POST_CONTENT = "Content";
    private static final String POST_PUBLISHED_DATE = "PublishedDate";
    private static final String POST_UPDATED_DATE = "UpdatedDate";
    private static final String POST_SUBREDDIT_NAME = "SubredditName";
    
    // Post CRUD
    private static final String CREATE_POST = "{ CALL spCreatePost (?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_POST = "{ CALL spUpdatePost (?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_POST = "{ CALL spDeletePost (?) }";
    private static final String SELECT_POST = "{ CALL spSelectPost (?) }";
    private static final String SELECT_POSTS = "{ CALL spSelectPosts }";
    private static final String SELECT_POSTS_BY_SUBREDDIT = "{ CALL spSelectPostsBySubreddit (?) }";
    
    // Comment
    private static final String ID_COMMENT = "Id";
    private static final String COMMENT_REDDIT_ID = "RedditId";
    private static final String COMMENT_TITLE = "Title";
    private static final String COMMENT_AUTHOR_ID = "AuthorId";
    private static final String COMMENT_AUTHOR_NAME = "AuthorName";
    private static final String COMMENT_AUTHOR_LINK = "AuthorLink";
    private static final String COMMENT_LINK = "Link";
    private static final String COMMENT_CONTENT = "Content";
    private static final String COMMENT_UPDATED_DATE = "UpdatedDate";
    private static final String COMMENT_SUBREDDIT_NAME = "SubredditName";
    private static final String COMMENT_POST_ID = "PostId";
    
    // Comment CRUD
    private static final String CREATE_COMMENT = "{ CALL spCreateComment (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_COMMENT = "{ CALL spUpdateComment (?,?,?,?,?,?,?,?) }";
    private static final String DELETE_COMMENT = "{ CALL spDeleteComment (?) }";
    private static final String SELECT_COMMENT = "{ CALL spSelectComment (?) }";
    private static final String SELECT_COMMENTS = "{ CALL spSelectComments }";
    private static final String SELECT_COMMENTS_BY_POST = "{ CALL spSelectCommentsByPost (?) }";
    private static final String DELETE_ALL = "{ CALL spDeleteAll }";
    
    @Override
    public int createUser(User user) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)){
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD_HASH, user.getPasswordHash());
            stmt.setString(PASSWORD_SALT, user.getPasswordSalt());
            stmt.setInt(USERROLE_ID, user.getUserRoleId());
            stmt.registerOutParameter(ID_USER, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public void updateUser(int id, User user) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER)){
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD_HASH, user.getPasswordHash());
            stmt.setString(PASSWORD_SALT, user.getPasswordSalt());
            stmt.setInt(USERROLE_ID, user.getUserRoleId());
            stmt.setInt(ID_USER, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER)){
            stmt.setInt(ID_USER, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER)){
            stmt.setInt(ID_USER, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD_HASH),
                            rs.getString(PASSWORD_SALT),
                            rs.getInt(USERROLE_ID)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> selectUsers() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USERS); ResultSet rs = stmt.executeQuery()){
            
            while (rs.next()) {
                users.add(new User(
                        rs.getInt(ID_USER),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD_HASH),
                        rs.getString(PASSWORD_SALT),
                        rs.getInt(USERROLE_ID)
                ));
            }
        }
        return users;
    }

    @Override
    public int createAuthor(Author author) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_AUTHOR)){
            stmt.setString(AUTHOR_NAME, author.getName());
            stmt.setString(AUTHOR_LINK, author.getLink());
            stmt.registerOutParameter(ID_AUTHOR, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_AUTHOR);
        }
    }

    @Override
    public void createAuthors(List<Author> authors) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_AUTHOR)){
            for (Author author : authors) {
                stmt.setString(AUTHOR_NAME, author.getName());
                stmt.setString(AUTHOR_LINK, author.getLink());
                stmt.registerOutParameter(ID_AUTHOR, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateAuthor(int id, Author author) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_AUTHOR)){
            stmt.setString(AUTHOR_NAME, author.getName());
            stmt.setString(AUTHOR_LINK, author.getLink());
            stmt.registerOutParameter(ID_AUTHOR, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAuthor(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_AUTHOR)){
            stmt.setInt(ID_AUTHOR, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Author> selectAuthor(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_AUTHOR)){
            stmt.setInt(ID_AUTHOR, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Author(
                            rs.getInt(ID_USER),
                            rs.getString(AUTHOR_NAME),
                            rs.getString(AUTHOR_LINK)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Author> selectAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_AUTHORS); ResultSet rs = stmt.executeQuery()){
            
            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt(ID_AUTHOR),
                        rs.getString(AUTHOR_NAME),
                        rs.getString(AUTHOR_LINK)
                ));
            }
        }
        return authors;
    }

    @Override
    public int createPost(Post post) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_POST)){
            stmt.setString(POST_REDDIT_ID, post.getRedditId());
            stmt.setString(POST_TITLE, post.getTitle());
            stmt.setString(POST_AUTHOR_NAME, post.getAuthor().getName());
            stmt.setString(POST_AUTHOR_LINK, post.getAuthor().getLink());
            stmt.setString(POST_LINK, post.getLink());
            stmt.setString(POST_THUMBNAIL_LINK, post.getThumbnailLink());
            stmt.setString(POST_CONTENT, post.getContent());
            stmt.setString(POST_PUBLISHED_DATE, post.getPublishedDate().format(Post.DATE_FORMATTER));
            stmt.setString(POST_UPDATED_DATE, post.getUpdatedDate().format(Post.DATE_FORMATTER));
            stmt.setString(POST_SUBREDDIT_NAME, post.getSubredditName());
            stmt.registerOutParameter(ID_POST, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_POST);
        }
    }

    @Override
    public void createPosts(List<Post> posts) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_POST)){
            for (Post post : posts) {
                stmt.setString(POST_REDDIT_ID, post.getRedditId());
                stmt.setString(POST_TITLE, post.getTitle());
                stmt.setString(POST_AUTHOR_NAME, post.getAuthor().getName());
                stmt.setString(POST_AUTHOR_LINK, post.getAuthor().getLink());
                stmt.setString(POST_LINK, post.getLink());
                stmt.setString(POST_THUMBNAIL_LINK, post.getThumbnailLink());
                stmt.setString(POST_CONTENT, post.getContent());
                stmt.setString(POST_PUBLISHED_DATE, post.getPublishedDate().format(Post.DATE_FORMATTER));
                stmt.setString(POST_UPDATED_DATE, post.getUpdatedDate().format(Post.DATE_FORMATTER));
                stmt.setString(POST_SUBREDDIT_NAME, post.getSubredditName());
                stmt.registerOutParameter(ID_POST, Types.INTEGER);
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updatePosts(int id, Post post) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_POST)){
            stmt.setString(POST_REDDIT_ID, post.getRedditId());
            stmt.setString(POST_TITLE, post.getTitle());
            stmt.setInt(POST_AUTHOR_ID, post.getAuthor().getId());
            stmt.setString(POST_LINK, post.getLink());
            stmt.setString(POST_THUMBNAIL_LINK, post.getThumbnailLink());
            stmt.setString(POST_CONTENT, post.getContent());
            stmt.setString(POST_PUBLISHED_DATE, post.getPublishedDate().format(Post.DATE_FORMATTER));
            stmt.setString(POST_UPDATED_DATE, post.getUpdatedDate().format(Post.DATE_FORMATTER));
            stmt.setString(POST_SUBREDDIT_NAME, post.getSubredditName());
            stmt.registerOutParameter(ID_POST, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePost(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_POST)){
            stmt.setInt(ID_POST, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Post> selectPost(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_POST)){
            stmt.setInt(ID_POST, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Post(
                            rs.getInt(ID_POST),
                            rs.getString(POST_REDDIT_ID),
                            rs.getString(POST_TITLE),
                            new Author(
                                rs.getInt(POST_AUTHOR_ID),
                                rs.getString(POST_AUTHOR_NAME),
                                rs.getString(POST_AUTHOR_LINK)
                            ),
                            rs.getString(POST_LINK),
                            rs.getString(POST_THUMBNAIL_LINK),
                            rs.getString(POST_CONTENT),
                            LocalDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                            LocalDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                            rs.getString(POST_SUBREDDIT_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Post> selectPosts() throws Exception {
        List<Post> posts = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_POSTS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                posts.add(new Post(
                        rs.getInt(ID_POST),
                        rs.getString(POST_REDDIT_ID),
                        rs.getString(POST_TITLE),
                        new Author(
                            rs.getInt(POST_AUTHOR_ID),
                            rs.getString(POST_AUTHOR_NAME),
                            rs.getString(POST_AUTHOR_LINK)
                        ),
                        rs.getString(POST_LINK),
                        rs.getString(POST_THUMBNAIL_LINK),
                        rs.getString(POST_CONTENT),
                        LocalDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                        LocalDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                        rs.getString(POST_SUBREDDIT_NAME)
                ));
            }
        }
        return posts;
    }

    @Override
    public int createComment(Comment comment) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_COMMENT)){
            stmt.setString(COMMENT_REDDIT_ID, comment.getRedditId());
            stmt.setString(COMMENT_TITLE, comment.getTitle());
            stmt.setString(COMMENT_AUTHOR_NAME, comment.getAuthor().getName());
            stmt.setString(COMMENT_AUTHOR_LINK, comment.getAuthor().getLink());
            stmt.setString(COMMENT_LINK, comment.getLink());
            stmt.setString(COMMENT_CONTENT, comment.getContent());
            stmt.setString(COMMENT_UPDATED_DATE, comment.getUpdatedDate().format(Comment.DATE_FORMATTER));
            stmt.setString(COMMENT_SUBREDDIT_NAME, comment.getSubredditName());
            stmt.registerOutParameter(ID_COMMENT, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(ID_COMMENT);
        }
    }

    @Override
    public void createComments(List<Comment> comments) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_COMMENT)){
            for (Comment comment : comments) {
                stmt.setString(COMMENT_REDDIT_ID, comment.getRedditId());
                stmt.setString(COMMENT_TITLE, comment.getTitle());
                stmt.setString(COMMENT_AUTHOR_NAME, comment.getAuthor().getName());
                stmt.setString(COMMENT_AUTHOR_LINK, comment.getAuthor().getLink());
                stmt.setString(COMMENT_LINK, comment.getLink());
                stmt.setString(COMMENT_CONTENT, comment.getContent());
                stmt.setString(COMMENT_UPDATED_DATE, comment.getUpdatedDate().format(Comment.DATE_FORMATTER));
                stmt.setString(COMMENT_SUBREDDIT_NAME, comment.getSubredditName());
                stmt.registerOutParameter(ID_COMMENT, Types.INTEGER);
            }
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateComment(int id, Comment comment) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_COMMENT)){
            stmt.setString(COMMENT_REDDIT_ID, comment.getRedditId());
            stmt.setString(COMMENT_TITLE, comment.getTitle());
            stmt.setInt(COMMENT_AUTHOR_ID, comment.getAuthor().getId());
            stmt.setString(COMMENT_LINK, comment.getLink());
            stmt.setString(COMMENT_CONTENT, comment.getContent());
            stmt.setString(COMMENT_UPDATED_DATE, comment.getUpdatedDate().format(Comment.DATE_FORMATTER));
            stmt.setString(COMMENT_SUBREDDIT_NAME, comment.getSubredditName());
            stmt.registerOutParameter(ID_COMMENT, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteComment(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_COMMENT)){
            stmt.setInt(ID_COMMENT, id);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Comment> selectComment(int id) throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_COMMENT)){
            stmt.setInt(ID_COMMENT, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Comment(
                        rs.getInt(ID_POST),
                        rs.getString(POST_REDDIT_ID),
                        rs.getString(POST_TITLE),
                        new Author(
                            rs.getInt(POST_AUTHOR_ID),
                            rs.getString(POST_AUTHOR_NAME),
                            rs.getString(POST_AUTHOR_LINK)
                        ),
                        rs.getString(POST_LINK),
                        rs.getString(POST_CONTENT),
                        LocalDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                        rs.getString(POST_SUBREDDIT_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Comment> selectComments() throws Exception {
        List<Comment> comments = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_COMMENTS); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt(ID_COMMENT),
                        rs.getString(COMMENT_REDDIT_ID),
                        rs.getString(COMMENT_TITLE),
                        new Author(
                            rs.getInt(COMMENT_AUTHOR_ID),
                            rs.getString(COMMENT_AUTHOR_NAME),
                            rs.getString(COMMENT_AUTHOR_LINK)
                        ),
                        rs.getString(COMMENT_LINK),
                        rs.getString(COMMENT_CONTENT),
                        LocalDateTime.parse(rs.getString(COMMENT_UPDATED_DATE), Comment.DATE_FORMATTER),
                        rs.getString(COMMENT_SUBREDDIT_NAME)
                ));
            }
        }
        return comments;
    }

    @Override
    public List<Post> selectPostsBySubreddit(String subredditName) throws Exception {
        List<Post> posts = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_POSTS_BY_SUBREDDIT); ResultSet rs = stmt.executeQuery()) {
        
            stmt.setString(POST_SUBREDDIT_NAME, subredditName);
        
            while (rs.next()) {
                posts.add(new Post(
                    rs.getInt(ID_POST),
                    rs.getString(POST_REDDIT_ID),
                    rs.getString(POST_TITLE),
                    new Author(
                        rs.getInt(POST_AUTHOR_ID),
                        rs.getString(POST_AUTHOR_NAME),
                        rs.getString(POST_AUTHOR_LINK)
                    ),
                    rs.getString(POST_LINK),
                    rs.getString(POST_THUMBNAIL_LINK),
                    rs.getString(POST_CONTENT),
                    LocalDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                    LocalDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                    rs.getString(POST_SUBREDDIT_NAME)
                ));
            }
        }
        return posts;
    }

    @Override
    public List<Comment> selectCommentsbyPost(int postId) throws Exception {
        List<Comment> comments = new ArrayList<>();
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_COMMENTS_BY_POST); ResultSet rs = stmt.executeQuery()) {
        
            stmt.setInt(COMMENT_POST_ID, postId);
        
            while (rs.next()) {
                comments.add(new Comment(
                    rs.getInt(ID_COMMENT),
                    rs.getString(COMMENT_REDDIT_ID),
                    rs.getString(COMMENT_TITLE),
                    new Author(
                        rs.getInt(COMMENT_AUTHOR_ID),
                        rs.getString(COMMENT_AUTHOR_NAME),
                        rs.getString(COMMENT_AUTHOR_LINK)
                    ),
                    rs.getString(COMMENT_LINK),
                    rs.getString(COMMENT_CONTENT),
                    LocalDateTime.parse(rs.getString(COMMENT_UPDATED_DATE), Comment.DATE_FORMATTER),
                    rs.getString(COMMENT_SUBREDDIT_NAME)
                ));
            }
        }
        return comments;
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ALL)) { 
            stmt.execute();
        }
    }
}
