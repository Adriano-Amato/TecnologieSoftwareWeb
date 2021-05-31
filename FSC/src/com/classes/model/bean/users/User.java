package com.classes.model.bean.users;

public class User {

	private String username;
	private String password;
	private String nome;
	private String cognome;
	
	public User() {
		nome = "";
		cognome = "";
		username = "";
		password = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cogome) {
		this.cognome = cogome;
	}

	protected static String adminUsername = "admin";

	protected static String adminPassword = "admin";

	public static boolean isAdmin(User user){
		if(user.username.equals(adminUsername) && user.password.equals(adminPassword)) return true;
		else return false;
	}

	public static boolean isAdmin(String username, String password){
		if(username.equals(adminUsername) && password.equals(adminPassword)) return true;
		else return false;
	}

}
