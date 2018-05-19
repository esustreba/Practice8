package ua.nure.strebkov.Practice8.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.strebkov.Practice8.db.entity.*;

public class DBManager {

	
	private static DBManager instance = null;
	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	private static final String URL = "jdbc:postgresql://localhost/usersDB?user=postgres&password=esu7163511";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_CREATE_NEW_USER = 
			"INSERT INTO public.users VALUES (DEFAULT, ?)";

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id=?";

	private static final String SQL_UPDATE_USER = "UPDATE users SET login=?, name=? WHERE id=?";

	///////////////////////////

	

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() {
		// nothing to do
	}

	/////////////////////////

	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(URL);
		return con;
	}

	////////////////////////////
	
	

	
	

	public boolean insertUser(User user) throws DBException {
		boolean res = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_USER,
					Statement.RETURN_GENERATED_KEYS);

			int k = 1;
			pstmt.setString(k++, user.getLogin());			

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
					res = true;
				}
			}
		} catch (SQLException ex) {
			// (1) write to log
			ex.printStackTrace();
			
			// (2)
			throw new DBException("Cannot create a user:" +  user, ex);
		} finally {
			close(con);
		}
		return res;
	}

	

	public List<User> findAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);

			while (rs.next()) {
				users.add(extractUser(rs));
			}
		} catch (SQLException ex) {
			// (1) write to log
			ex.printStackTrace();
			// log.error("Cannot obtain a user by login", ex);
			
			// (2)
			throw new DBException("Cannot obtain a user by login", ex);
		} finally {
			close(con);
		}
		return users;
	}

	//////////////////////
	// utils
	
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setName(rs.getString("name"));
		return user;
	}



	private void close(AutoCloseable ac) {
		if (ac != null) {
			try {
				ac.close();
			} catch (Exception ex) {
				throw new IllegalStateException("Cannot close " + ac);
			}
		}
	}

		



}
