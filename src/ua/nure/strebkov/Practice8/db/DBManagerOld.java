package ua.nure.strebkov.Practice8.db;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.List;
	
	import ua.nure.strebkov.Practice8.db.entity.Group;
	import ua.nure.strebkov.Practice8.db.entity.User;
	

	public class DBManagerOld {
		private static final String URL = "jdbc:postgresql://localhost/usersDB?user=postgres&password=esu7163511";
			

		private static DBManagerOld instance = null;
		private static Connection con = null;
		private static PreparedStatement ps = null;
		private static Statement stmt = null;
		private static ResultSet rs = null;

		private DBManagerOld() throws SQLException {
		}

		public static synchronized DBManagerOld getInstance() throws SQLException {
			if (instance == null) {
				instance = new DBManagerOld();
			}
			return instance;
		}

		public User getUser(String login) throws SQLException {
			con = DriverManager.getConnection(URL);
			User user = null;

			ps = con.prepareStatement("SELECT id FROM users WHERE login=?");
			ps.setString(1, login);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = User.createUser(login);
				user.setId(rs.getInt(1));
			}
			rs.close();
			ps.close();
			con.close();
			return user;
		}

		public Group getGroup(String name) throws SQLException {
			con = DriverManager.getConnection(URL);
			Group group = null;

			ps = con.prepareStatement("SELECT id FROM groups WHERE name=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next()) {
				group = Group.createGroup(name);
				group.setId(rs.getInt("id"));
			}
			rs.close();
			ps.close();
			con.close();
			return group;
		}

		public void insertUser(User u) throws SQLException {
			con = DriverManager.getConnection(URL);

			ps = con.prepareStatement("SELECT login FROM users WHERE login = ?");
			ps.setString(1, u.getLogin());
			if (!ps.executeQuery().next()) {
				ps = con.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?)");
				ps.setString(1, u.getLogin());
				ps.executeUpdate();
				ps = con.prepareStatement("SELECT id FROM users WHERE login=?");
				ps.setString(1, u.getLogin());
				rs = ps.executeQuery();
				if (rs.next()) {
					u.setId(rs.getInt(1));
				}
				rs.close();
			}
			ps.close();
			con.close();

		}

		public List<User> findAllUsers() throws SQLException {
			List<User> users = new ArrayList<>();
			con = DriverManager.getConnection(URL);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM users");
			while (rs.next()) {
				User user = User.createUser(rs.getString("login"));
				user.setId(rs.getInt("id"));
				users.add(user);
			}
			rs.close();
			stmt.close();
			con.close();
			return users;
		}

		public void insertGroup(Group group) throws SQLException {
			con = DriverManager.getConnection(URL);
			ps = con.prepareStatement("SELECT name FROM groups WHERE name=?");
			ps.setString(1, group.getName());
			if (!ps.executeQuery().next()) {
				ps = con.prepareStatement("INSERT INTO groups VALUES(DEFAULT, ?)");
				ps.setString(1, group.getName());
				ps.executeUpdate();
				ps = con.prepareStatement("SELECT id FROM groups WHERE name=?");
				ps.setString(1, group.getName());
				rs = ps.executeQuery();
				if (rs.next()) {
					group.setId(rs.getInt(1));
				}
				rs.close();
			}
			ps.close();
			con.close();
		}

		public List<Group> findAllGroups() throws SQLException {
			List<Group> groups = new ArrayList<>();
			con = DriverManager.getConnection(URL);
			rs = con.createStatement().executeQuery("SELECT * FROM groups");

			while (rs.next()) {
				Group group = Group.createGroup(rs.getString("name"));
				group.setId(rs.getInt("id"));
				groups.add(group);
			}
			rs.close();
			stmt.close();
			con.close();
			return groups;
		}

		public void setGroupForUser(User user, Group... group) throws SQLException {
			con = DriverManager.getConnection(URL);

			for (Group g : group) {
				if(g == null) return;
				ps = con.prepareStatement("SELECT * FROM users_groups WHERE user_id=? AND groups_id=?");
				ps.setInt(1, user.getId());
				ps.setInt(2, g.getId());
				rs = ps.executeQuery();
			}

			con.setAutoCommit(false);
			if (!rs.next()) {
				try {
					for (Group g : group) {
						ps = con.prepareStatement("INSERT INTO users_groups VALUES(?, ?)");
						ps.setInt(1, user.getId());
						ps.setInt(2, g.getId());
						ps.executeUpdate();
					}
					con.commit();
				} catch (SQLException e) {
					con.rollback();
					throw new SQLException();
				} finally {
					rs.close();
					ps.close();
					con.close();
				}
			}
			rs.close();
			ps.close();
			con.close();
		}

		public List<Group> getUserGroups(User user) throws SQLException {
			List<Group> groups = new ArrayList<>();
			con = DriverManager.getConnection(URL);

			ps = con.prepareStatement(
					"SELECT g.* FROM groups g INNER JOIN users_groups ug ON g.id = ug.groups_id WHERE ug.user_id=?");
			ps.setInt(1, user.getId());
			rs = ps.executeQuery();

			while (rs.next()) {
				Group group = Group.createGroup(rs.getString("name"));
				group.setId(rs.getInt("id"));
				groups.add(group);
			}
			rs.close();
			ps.close();
			con.close();
			return groups;
		}
		
		public void deleteGroup(Group group) throws SQLException {
			if(group == null) return;
			con = DriverManager.getConnection(URL);
			
			ps = con.prepareStatement("DELETE FROM groups WHERE id=?");
			ps.setInt(1, group.getId());
			ps.executeUpdate();
			
			ps.close();
			con.close();
		}
		
		public void updateGroup(Group group) throws SQLException {
			if(group == null) return;
			con = DriverManager.getConnection(URL);
			
			ps = con.prepareStatement("UPDATE groups SET name=? WHERE id=?");
			ps.setString(1, group.getName());
			ps.setInt(2, group.getId());
			ps.executeUpdate();
			
			ps.close();
			con.close();
		}
	
	}

