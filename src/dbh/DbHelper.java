package dbh;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbHelper {
	private static final String dbUrl = "jdbc:sqlserver://localhost:4096;databaseName=kullanici_kayit;encrypt=true;trustServerCertificate=true;";
	private static final String username="sqluser";
	private static final String password="sqluser123456*-";
	
	public static Connection getConnection() throws SQLException {
		return(Connection) DriverManager.getConnection(dbUrl,username,password);
	}
	
	public void ShowError(SQLException exception) {
		System.out.println("Error : "+exception.getMessage());
		System.out.println("Error Code : "+exception.getErrorCode());
	}

}
