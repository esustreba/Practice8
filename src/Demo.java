import java.sql.SQLException;
import java.util.List;

import ua.nure.strebkov.Practice8.db.DBException;
import ua.nure.strebkov.Practice8.db.DBManager;
import ua.nure.strebkov.Practice8.db.entity.User;
import ua.nure.strebkov.Practice8.db.entity.Group;

public class Demo {
	private static <T> void printList(List<T> list) {
		for (T element : list) {
			System.out.println(element);
		}
	}

	public static void main(String[] args) throws SQLException, DBException {
		DBManager dbManager = DBManager.getInstance();

		// Part 1
		System.out.println("=======Part1========");
		dbManager.insertUser(User.createUser("ivanov"));
		dbManager.insertUser(User.createUser("petrov"));
		dbManager.insertUser(User.createUser("obama"));
		printList(dbManager.findAllUsers());
	

	 // Part 2
	/*	System.out.println("=======Part2========");
		dbManager.insertGroup(Group.createGroup("teamB"));
		dbManager.insertGroup(Group.createGroup("teamC"));
		printList(dbManager.findAllGroups());


			// Part 3
		System.out.println("=======Part3========");
		User userPetrov = dbManager.getUser("petrov");
		User userIvanov = dbManager.getUser("ivanov");
		User userObama = dbManager.getUser("obama");

		Group teamA = dbManager.getGroup("teamA");
		Group teamB = dbManager.getGroup("teamB");
		Group teamC = dbManager.getGroup("teamC");

		dbManager.setGroupForUser(userIvanov, teamA);
		dbManager.setGroupForUser(userPetrov, teamA, teamB);
		dbManager.setGroupForUser(userObama, teamA, teamB, teamC);

		for (User user : dbManager.findAllUsers()) {
			System.out.println(user);
			System.out.println("------");
			printList(dbManager.getUserGroups(user));
			System.out.println("~~~~~~");
		}

		// Part 4
		System.out.println("=======Part4========");
		dbManager.deleteGroup(teamA);

		for (User user : dbManager.findAllUsers()) {
			System.out.println(user);
			printList(dbManager.getUserGroups(user));
			System.out.println("~~~~~~");
		}

		// Part 5
		System.out.println("=======Part5========");
		teamC.setName("teamX");
		dbManager.updateGroup(teamC);

		for (Group group : dbManager.findAllGroups()) {
			System.out.println(group);
		} */
	} 

}
