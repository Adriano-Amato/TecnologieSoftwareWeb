package com.classes.model.bean.users;

import com.classes.model.bean.entity.SquadraBean;

import java.io.Serializable;

public class IscrittoUserBean extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int goal;
	private int assist;
	private int minuti;
	private int eta;
	private SquadraBean squadra;
	private String codFis;

	public IscrittoUserBean() {
		super();
		goal = 0;
		assist = 0;
		minuti = 0;
		codFis = "";
		squadra = null;
	}

	public int getGoal() { return goal; }

	public void setGoal(int goal) { this.goal = goal; }
	
	public void addGoal(int goal) { this.goal += goal; }

	public void setAssist(int assist) { this.assist = assist; }
	
	public int getAssist() { return assist; }

	public void addAssist(int assist) { this.assist += assist; }

	public int getMinuti() { return minuti; }

	public void setMinuti(int minuti){ this.minuti = minuti; }

	public void addMinuti(int minuti) { this.minuti += minuti; }

	public SquadraBean getSquadra() { return squadra; }

	public void setSquadra(SquadraBean squadra) { this.squadra = squadra; }

	public String getCodFis() { return codFis; }

	public void setCodFis(String codFis) { this.codFis = codFis; }

	public int getEta() { return eta; }

	public void setEta(int eta) { this.eta = eta; }

}
