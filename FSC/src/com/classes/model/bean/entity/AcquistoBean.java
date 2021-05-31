package com.classes.model.bean.entity;

import com.classes.model.bean.products.ProductBean;
import com.classes.model.bean.users.User;

public class AcquistoBean {

    private int quantita;
    private ProductBean prodotto;
    private User utente;
    private int codAcquisto;

    public AcquistoBean(){
        this.quantita = 0;
        this.prodotto = null;
        this.utente = null;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public ProductBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProductBean prodotto) {
        this.prodotto = prodotto;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }


    public int getCodAcquisto() {
        return codAcquisto;
    }

    public void setCodAcquisto(int codAcquisto) {
        this.codAcquisto = codAcquisto;
    }

}
