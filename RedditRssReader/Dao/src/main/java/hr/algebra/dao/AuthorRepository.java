/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dao;

import hr.algebra.dao.model.Author;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author miki
 */
public interface AuthorRepository {
    int createAuthor(Author author) throws Exception;
    void createAuthors(List<Author> authors) throws Exception;
    void updateAuthor(int id, Author author) throws Exception;
    void deleteAuthor(int id) throws Exception;
    Optional<Author> selectAuthor(int id) throws Exception;
    List<Author> selectAuthors() throws Exception;
}
