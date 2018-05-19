package ua.nure.strebkov.Practice8.db.entity;

public class Group {
	private int id;
	private String name;
	
	private Group() {}
	
	public static Group createGroup(String name) {
		Group group = new Group();
		group.setName(name);
		return group;
	}
	
	public String toString() {
		return id + ": " + name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
