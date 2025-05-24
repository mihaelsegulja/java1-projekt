/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.CommentRepository;
import hr.algebra.dao.model.Author;
import hr.algebra.dao.model.Comment;
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
public class CommentSqlRepository implements CommentRepository {
    
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
    
    private static final String CREATE_COMMENT = "{ CALL spCreateComment (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_COMMENT = "{ CALL spUpdateComment (?,?,?,?,?,?,?,?) }";
    private static final String DELETE_COMMENT = "{ CALL spDeleteComment (?) }";
    private static final String SELECT_COMMENT = "{ CALL spSelectComment (?) }";
    private static final String SELECT_COMMENTS = "{ CALL spSelectComments }";
    
    private static final String SELECT_COMMENTS_BY_POST = "{ CALL spSelectCommentsByPost (?) }";
    
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
            
                stmt.executeUpdate();
            }
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
                        OffsetDateTime.parse(rs.getString(COMMENT_UPDATED_DATE), Post.DATE_FORMATTER),
                        rs.getString(COMMENT_SUBREDDIT_NAME)
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
                        OffsetDateTime.parse(rs.getString(COMMENT_UPDATED_DATE), Comment.DATE_FORMATTER),
                        rs.getString(COMMENT_SUBREDDIT_NAME)
                ));
            }
        }
        return comments;
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
                    OffsetDateTime.parse(rs.getString(COMMENT_UPDATED_DATE), Comment.DATE_FORMATTER),
                    rs.getString(COMMENT_SUBREDDIT_NAME)
                ));
            }
        }
        return comments;
    }
}
