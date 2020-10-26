package bean;

public class User {
	private String password;// √‹¬Î
	private String number;// —ß∫≈
	private String name;// –’√˚
	private String class_name;//∞‡º∂

	public User(String number, String password, String name, String class_name) {
		this.password = password;
		this.number = number;
		this.name = name;
		this.class_name = class_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
}
