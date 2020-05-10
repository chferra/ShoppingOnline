CREATE TABLE Utenti(
  ID INT(8) PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(16) NOT NULL,
  cognome VARCHAR(16) NOT NULL,
  dataNascita DATE NOT NULL,
  email VARCHAR(32) NOT NULL,
  password VARCHAR(32) NOT NULL,
  negoziante tinyint(1) NOT NULL,
  FOREIGN KEY (CodIndirizzoPredefinito) REFERENCES Indirizzi(ID) 
	ON DELETE CASCADE 
	ON UPDATE CASCADE,
  FOREIGN KEY (CodFotografia) REFERENCES Fotografie(ID) 
	ON DELETE CASCADE
	ON UPDATE CASCADE
);


CREATE TABLE Indirizzo(
	ID INT(11) PRIMARY KEY AUTO_INCREMENT,
	
	destinatario VARCHAR(32), 
	telefono INT(11), 
	Stato VARCHAR(32), 
	provincia VARCHAR(2), 
	citta VARCHAR(10), 
	via VARCHAR(10), 
	nCivico INT(2), 
	codPostale VARCHAR(5),
	*CodUtente INT(11) REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);


CREATE TABLE Fotografia(
	 ID INT(11) PRIMARY KEY,
	 percorso VARCHAR(32) NOT NULL,
	 *CodNegozio INT(11) REFERENCES Negozio(ID),
	 *CodProdotto INT(11) REFERENCES Prodotto(ID)
 );


CREATE TABLE Carrello(
	 ID INT(11) PRIMARY KEY,
	 
	 *CodUtente INT(11) REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	 *CodNegozio INT(11) REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
		
	ordineEffettuato BOOLEAN
 );



CREATE TABLE Comprende(
	ID INT(11) PRIMARY KEY,
	*CodNegozio INT(11) REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodCarrello INT(11) REFERENCES Carrello(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodProdotto INT(11) REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	quantitaCliente INT(10), 
	quantitaNegoziante INT(11), 
	quantitaConfermata INT(11)
);



CREATE TABLE  Ordine(
	ID INT(11) PRIMARY KEY,
	dataOraAcquisto DATETIME,
	dataOraConsegna DATETIME,
	consegnaDomicilio BOOLEAN, 
	fattura VARCHAR(32), 
	stato VARCHAR(32), 
	confermaCliente BOOLEAN, 
	confermaNegoziante BOOLEAN,
	
	*CodCarrello INT(11) REFERENCES Carrello(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodIndirizzoConsegna INT(11) REFERENCES Indirizzo(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
	
);


CREATE TABLE Prodotto(
	ID INT(11) PRIMARY KEY,
	nome VARCHAR(32), 
	descrizione VARCHAR(32), 
	prezzo INT(10),
	
	*CodUtente INT(11) REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodNegozio INT(11) REFERENCES negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);



CREATE TABLE Appartiene(
	*CODPRODOTTO INT(11) REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CODCATEGORIA INT(11) REFERENCES Gategoria(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE

);



CREATE TABLE Categoria(

	ID INT(11) PRIMARY KEY, 
	nome VARCHAR(32)
);



CREATE TABLE Caratteristica(
	ID INT(11) PRIMARY KEY, 
	nome VARCHAR(32), 
	valore INT(10),
	
	*CODPRODOTTO INT(11) REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodUtente INT(11) REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);



CREATE TABLE Negozio(
	ID INT(11) PRIMARY KEY,  
	nome VARCHAR(32), 
	
	*CodIndirizzo INT(11) REFERENCES Indirizzo(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);



CREATE TABLE Caratterizza(
	*CodNegozio INT(11) REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodCategoria INT(11) REFERENCES Gategoria(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);



