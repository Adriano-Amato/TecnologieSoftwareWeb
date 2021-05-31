package com.classes.model.bean.entity;

public class NotiziaBean {

    private int codice;
    private String titolo;
    private String articolo;

    public NotiziaBean(){
        this.codice=-1;
        this.titolo="";
        this.articolo="";
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getArticolo() {
        return articolo;
    }

    public void setArticolo(String articolo) {
        this.articolo = articolo;
    }
}
