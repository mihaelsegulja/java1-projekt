/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dao;

import hr.algebra.dao.model.Comment;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author miki
 */
public interface CommentRepository {
    int createComment(Comment comment) throws Exception;
    void createComments(List<Comment> comments) throws Exception;
    void updateComment(int id, Comment comment) throws Exception;
    void deleteComment(int id) throws Exception;
    Optional<Comment> selectComment(int id) throws Exception;
    List<Comment> selectComments() throws Exception;
    List<Comment> selectCommentsbyPost(int postId) throws Exception;
}
