package com.classes.model.bean.entity;

import java.io.Serializable;

public class TorneoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SquadraBean winner;
	private String nome;
	private int edizione;
	private String premio;
	
	public TorneoBean() {
		nome = "";
		edizione = 0;
		premio="";
		winner=null;
	}

	public SquadraBean getWinner() {
		return winner;
	}

	public void setWinner(SquadraBean winner) {
		this.winner = winner;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEdizione() {
		return edizione;
	}

	public void setEdizione(int edizione) {
		this.edizione = edizione;
	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}

}
