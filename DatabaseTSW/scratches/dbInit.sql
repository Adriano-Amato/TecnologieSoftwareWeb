USE scuolacalciodb;

DROP TABLE IF EXISTS squadra;
CREATE TABLE squadra (
    nome VARCHAR(20) NOT NULL,
    etabambini SMALLINT NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    PRIMARY KEY (nome)
);

DROP TABLE IF EXISTS dipendenti;
CREATE TABLE dipendenti(
    codFis CHAR(16) NOT NULL,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    ruolo VARCHAR(20) NOT NULL,
    squadra VARCHAR(20) NOT NULL,
    PRIMARY KEY (codFis),
    FOREIGN KEY (squadra) REFERENCES squadra(nome) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS utente;
CREATE TABLE utente(
    username VARCHAR(20) NOT NULL,
    psw VARCHAR(20) NOT NULL,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL,
    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS iscritto;
CREATE TABLE iscritto(
    codFis CHAR(16) NOT NULL,
    utente CHAR(16) NOT NULL,
    eta SMALLINT NOT NULL,
    goal SMALLINT NOT NULL,
    assist SMALLINT NOT NULL,
    minuti SMALLINT NOT NULL,
    squadra VARCHAR(20) NOT NULL,
    FOREIGN KEY (utente) REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (squadra) REFERENCES squadra(nome) ON DELETE CASCADE ON UPDATE CASCADE

);

DROP TABLE IF EXISTS retta;
CREATE TABLE retta (
   progressivo SMALLINT AUTO_INCREMENT,
   importo SMALLINT NOT NULL,
   sconto SMALLINT NOT NULL DEFAULT 0,
   iscritto CHAR(16) NOT NULL,
   dataInizio DATE NOT NULL,
   dataFine DATE NOT NULL,
   PRIMARY KEY (progressivo),
   FOREIGN KEY (iscritto) REFERENCES iscritto(utente) ON DELETE CASCADE ON UPDATE CASCADE
) AUTO_INCREMENT = 0;

DROP TABLE IF EXISTS prodotto;
CREATE TABLE prodotto(
    nome CHAR(16) NOT NULL,
    descrizione CHAR(200) NOT NULL,
    prezzo SMALLINT NOT NULL,
    codId SMALLINT NOT NULL,
    quantita SMALLINT NOT NULL,
    PRIMARY KEY (codId)
);

DROP TABLE IF EXISTS acquisto;
CREATE TABLE acquisto(
    utente CHAR(20) NOT NULL,
    prodotto SMALLINT NOT NULL,
    quantita SMALLINT NOT NULL,
    codAcquisto SMALLINT AUTO_INCREMENT,
    PRIMARY KEY (codAcquisto),
    FOREIGN KEY (utente) REFERENCES utente(username) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (prodotto) REFERENCES prodotto(codId) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS notizia;
CREATE TABLE notizia (
	codice SMALLINT AUTO_INCREMENT,
    titolo VARCHAR(20) NOT NULL,
    articolo TEXT NOT NULL,
    PRIMARY KEY (codice)
);

DROP TABLE IF EXISTS immagini;
CREATE TABLE immagini (
    image MEDIUMBLOB,
    codId SMALLINT NOT NULL,
    FOREIGN KEY (codId) REFERENCES prodotto(codId) ON DELETE CASCADE ON UPDATE CASCADE
);