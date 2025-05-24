/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.PostRepository;
import hr.algebra.dao.model.Author;
import hr.algebra.dao.model.Post;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author miki
 */
public class PostSqlRepository implements PostRepository {
    
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
    
    private static final String CREATE_POST = "{ CALL spCreatePost (?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_POST = "{ CALL spUpdatePost (?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_POST = "{ CALL spDeletePost (?) }";
    private static final String SELECT_POST = "{ CALL spSelectPost (?) }";
    private static final String SELECT_POSTS = "{ CALL spSelectPosts }";
    
    private static final String SELECT_POSTS_BY_SUBREDDIT = "{ CALL spSelectPostsBySubreddit (?) }";
    
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
                            OffsetDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                            OffsetDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
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
                        OffsetDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                        OffsetDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                        rs.getString(POST_SUBREDDIT_NAME)
                ));
            }
        }
        return posts;
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
                    OffsetDateTime.parse(rs.getString(POST_PUBLISHED_DATE), Post.DATE_FORMATTER),
                    OffsetDateTime.parse(rs.getString(POST_UPDATED_DATE), Post.DATE_FORMATTER),
                    rs.getString(POST_SUBREDDIT_NAME)
                ));
            }
        }
        return posts;
    }
}
