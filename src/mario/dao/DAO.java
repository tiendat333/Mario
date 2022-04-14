/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */

public class DAO {
    public static Connection makeConnection(){
        Connection con = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-F0UT443\\TOLETROI:1433;databaseName=LEADERBOARD";
            //url = "jdbc:sqlserver://"+serverName+":"+portNumber +";databaseName="+dbName;
            con = DriverManager.getConnection(url,"sa","dat123456");
        
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        
        return con;
    }
    
}
