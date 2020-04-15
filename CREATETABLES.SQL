CREATE TABLE Utenti (

	ID INT(11) PRIMARY KEY PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(11) NOT NULL,
	cognome VARCHAR(11) NOT NULL, 
	dataNascita DATE NOT NULL CHECK(dataNascita < Now()),
	email VARCHAR(32) NOT NULL UNIQUE, 
	password VARCHAR(32) NOT NULL, 
	negoziante BOOLEAN
	
	CodIndirizzoPredefinito REFERENCES Indirizzo(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	CodFotografia REFERENCES Fotografia(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)





Indirizzo(
	ID INT(11) PRIMARY KEY PRIMARY KEY AUTO_INCREMENT,
	
	destinatario VARCHAR(32), 
	telefono INT(11), 
	Stato VARCHAR(32), 
	provincia VARCHAR(2), 
	citta VARCHAR(10), 
	via VARCHAR(10), 
	nCivico INT(2), 
	codPostale VARCHAR(5)
	
	*CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)
	- "provincia" deve essere un char di 2 caratteri
	- "codPostale" deve essere un char di 5 caratteri

-------------------------------------------------------------------------------------------------------------

Accesso(ID, 

	data DATE NOT NULL,
	Ora TIME NOT NULL, 
	IP INT(12) NOT NULL,

	*CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
) 

-------------------------------------------------------------------------------------------------------------

Fotografia(
	 ID INT(11) PRIMARY KEY,
	 percorso VARCHAR(32) NOT NULL,
	 
	 
	 *CodNegozio REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	 *CodProdotto REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
 )

-------------------------------------------------------------------------------------------------------------

Carrello(
	 ID INT(11) PRIMARY KEY,
	 
	 *CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	 *CodNegozio REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
 )

-------------------------------------------------------------------------------------------------------------

Comprende(
	ID INT(11) PRIMARY KEY,
	*CodNegozio REFERENCES Carrello(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodProdotto REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)

-------------------------------------------------------------------------------------------------------------

Ordine(
	ID INT(11) PRIMARY KEY,
	dataAcquisto DATE,
	oraAcquisto TIME, 
	dataConsegna DATE,
	oraConsegna TIME,
	consegnaDomicilio BOOLEAN, 
	fattura VARCHAR(32), ??
	
	*CodCarrello REFERENCES Carrello(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodIndirizzoConsegna REFERENCES Indirizzo(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	
)
	- "consegnaDomicilio" è un flag (tinyint), true o false

-------------------------------------------------------------------------------------------------------------

Prodotto(
	ID INT(11) PRIMARY KEY,
	nome VARCHAR(32), 
	descrizione VARCHAR(32), 
	prezzo INT(10)
	
	*CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodNegozio REFERENCES negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)

-------------------------------------------------------------------------------------------------------------

Appartiene(
	*CODPRODOTTO REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CODCATEGORIA REFERENCES Gategoria(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,

)

-------------------------------------------------------------------------------------------------------------

Categoria(

	ID INT(11) PRIMARY KEY, 
	nome VARCHAR(32), 
)

-------------------------------------------------------------------------------------------------------------

Caratteristica(
	ID INT(11) PRIMARY KEY, 
	nome VARCHAR(32), 
	valore INT(10) ?,
	
	*CODPRODOTTO REFERENCES Prodotto(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)

-------------------------------------------------------------------------------------------------------------

Negozio(
	ID INT(11) PRIMARY KEY,  
	nome VARCHAR(32), 
	
	*CodIndirizzo REFERENCES Indirizzo(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)

-------------------------------------------------------------------------------------------------------------

Caratterizza(
	*CodNegozio REFERENCES Negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodCategoria REFERENCES Gategoria(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
)

-------------------------------------------------------------------------------------------------------------

Gestisce(
	*CodUtente REFERENCES Utenti(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	*CodNegozio REFERENCES negozio(ID)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
 )