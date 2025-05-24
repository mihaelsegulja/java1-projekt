/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dao.sql;

import hr.algebra.dao.AdministrationRepository;
import java.sql.CallableStatement;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author miki
 */
public class AdministrationSqlRepository implements AdministrationRepository {
    
    private static final String DELETE_ALL = "{ CALL spDeleteAll }";
    
    @Override
    public void deleteAll() throws Exception {
        DataSource ds = DataSourceSingleton.getInstance();
        try (Connection con = ds.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ALL)) { 
            stmt.execute();
        }
    }
}
