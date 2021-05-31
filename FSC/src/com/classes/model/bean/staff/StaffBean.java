package com.classes.model.bean.staff;

import com.classes.model.bean.entity.SquadraBean;

import java.io.Serializable;

public class StaffBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String role;
	private String codFis;
	private String name;
	private String surname;
	private SquadraBean squadra;

	public StaffBean(){
		codFis = "";
		role = "";
		name = "";
		surname = "";
		squadra = null;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public SquadraBean getSquadra() {
		return squadra;
	}

	public void setSquadra(SquadraBean squadra) {
		this.squadra = squadra;
	}
}
