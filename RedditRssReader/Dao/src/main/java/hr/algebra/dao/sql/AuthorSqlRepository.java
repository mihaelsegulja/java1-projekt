/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.AuthorRepository;
import hr.algebra.dao.model.Author;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author miki
 */
public class AuthorSqlRepository implements AuthorRepository {
    
    private static final String ID_AUTHOR = "Id";
    private static final String AUTHOR_NAME = "Name";
    private static final String AUTHOR_LINK = "Link";
    
    private static final String CREATE_AUTHOR = "{ CALL spCreateAuthor (?,?,?) }";
    private static final String UPDATE_AUTHOR = "{ CALL spUpdateAuthor (?,?,?) }";
    private static final String DELETE_AUTHOR = "{ CALL spDeleteAuthor (?) }";
    private static final String SELECT_AUTHOR = "{ CALL spSelectAuthor (?) }";
    private static final String SELECT_AUTHORS = "{ CALL spSelectAuthors }";
    
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
            stmt.setInt(ID_AUTHOR, id);
            stmt.setString(AUTHOR_NAME, author.getName());
            stmt.setString(AUTHOR_LINK, author.getLink());
            
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
                            rs.getInt(ID_AUTHOR),
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
}
