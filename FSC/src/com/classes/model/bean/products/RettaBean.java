package com.classes.model.bean.products;

import com.classes.model.bean.users.IscrittoUserBean;

import java.io.Serializable;
import java.util.Date;

public class RettaBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int sconto;
	private int importo;
	private int progressivo;
	private IscrittoUserBean iscritto;
	private Date dataInizio, dataFine;

	public RettaBean(){
		sconto = 0;
		importo = 0;
		progressivo = 0;
		iscritto = null;
		dataInizio = null;
		dataFine = null;
	}
	
	public int getSconto() { return sconto; }
	
	public int getImporto() { return importo; }
	
	public int getProgressivo() { return progressivo; }
	
	public IscrittoUserBean getIscritto() { return iscritto; }
	
	public Date getDataInizio() { return dataInizio; }
	
	public Date getDataFine() { return dataFine; }
	
	public void setImporto(int imp) { this.importo = imp; }
	
	public void setProgressivo(int progressivo) { this.progressivo = progressivo; }
	
	public void setSconto(int sconto) { this.sconto = sconto; }

	public void setIscritto(IscrittoUserBean iscritto) { this.iscritto = iscritto; }
	
	public void setDataInizio(Date dat) { this.dataInizio = dat; }

	public void setDataFine(Date dat) { this.dataFine = dat; }
}
