package com.example.demosp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCCreateTable {

	private static final String DBURL = "jdbc:mysql://localhost:3306/mydb?user=usr&password=sql&"
			+ "useUnicode=true&characterEncoding=UTF-8";
	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";

	private static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");

		return c;
	}

	public static void main(String[] args) {
        Connection con = getConnection();
        Statement stmt =null;
        try {
            stmt = con.createStatement();
          /*  stmt.execute("CREATE OR REPLACE FUNCTION WhoAreThey(" +
                   "OUT error VARCHAR(128)," +
                   "IN office VARCHAR(10)) " +
                   "BEGIN "+
                   "SET error = NULL; "+
                   "IF office IS NULL THEN "+
                   "SET error = 'You need to pass in an office number'; "+
                   "ELSE "+
                   "  SELECT EmployeeID, Name FROM " + 
                   " employees WHERE office = office; "+
                   "END IF; "+
                   "END");
*/
            
            stmt.execute("CREATE OR REPLACE FUNCTION helloWorld(name text) RETURNS void AS $helloWorld$\r\n" + 
            		"DECLARE\r\n" + 
            		"BEGIN\r\n" + 
            		"    RAISE LOG 'Hello, %', name;\r\n" + 
            		"END;\r\n" + 
            		"$helloWorld$ LANGUAGE plpgsql;");
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("SQLException: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("SQLException: " + e.getMessage());
                }
            }
        }
    }

}
