/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.UserRepository;
import hr.algebra.dao.model.User;
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
public class UserSqlRepository implements UserRepository {
    
    private static final String ID_USER = "Id";
    private static final String USERNAME = "Username";
    private static final String PASSWORD_HASH = "PasswordHash";
    private static final String PASSWORD_SALT = "PasswordSalt";
    private static final String USERROLE_ID = "UserRoleId";
    
    private static final String CREATE_USER = "{ CALL spCreateUser (?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL spUpdateUser (?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL spDeleteUser (?) }";
    private static final String SELECT_USER = "{ CALL spSelectUser (?) }";
    private static final String SELECT_USERS = "{ CALL spSelectUsers }";
    
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
}
