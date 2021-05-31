package com.classes.model.bean.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.classes.model.bean.users.IscrittoUserBean;

public class SquadraBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String category;
	private int age_range;
//	private ArrayList<IscrittoUserBean> squad;
	
	public SquadraBean() {
		name = "";
		category = "";
		age_range = 0;
//		squad = new ArrayList<IscrittoUserBean>();
	}

//	public ArrayList<IscrittoUserBean> getSquad() {
//		return squad;
//	}
//
//	public void setSquad(ArrayList<IscrittoUserBean> squad){ this.squad = squad;}
//
//	public void addSquadMember(IscrittoUserBean squadMember) {
//		this.squad.add(squadMember);
//	}
//
//	public void removeSquadMember(IscrittoUserBean squadMember) {
//		this.squad.remove(squadMember);
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAge_range() {
		return age_range;
	}

	public void setAge_range(int age_range) {
		this.age_range = age_range;
	}
	
}
