package com.classes.model.bean.users;

import java.io.Serializable;

public class AdminUserBean extends User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public AdminUserBean() {
		super();
		setUsername("admin");
		setPassword("admin");
	}

}
