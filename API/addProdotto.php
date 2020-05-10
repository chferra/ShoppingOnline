<?php
	
	include_once(__DIR__ . '/../connector.php');
	
	$jsonString = file_get_contents('php://input');
    $parameters = json_decode($jsonString);
	
	$codNegozio = $parameters->{'addProdotto'}->{'codNegozio'};
	$nome = $parameters->{'addProdotto'}->{'nome'};
	$prezzo = $parameters->{'addProdotto'}->{'prezzo'};
	$parameters->{'addProdotto'}->{'categoria'};
	$parameters->{'addProdotto'}->{'descrizione'};
	$parameters->{'addProdotto'}->{'pathImmagine'};
	
	function validateParams(){
		global $codNegozio;
		global $nome;
		global $prezzo;
		global $categoria;
		global $descrizione;
		global $pathImmagine;
		
		if (!isset($nome) || !isset($prezzo) || !isset($categoria) || !isset($descrizione)){
            return false;
		}
		
		$prezzoNumerico = is_numeric($prezzo);
        
        if (!$prezzoNumerico) 
            throw new Exception("Bad request", 400);
		
		$codNumerico = is_numeric($codNegozio);
        
        if (!$codNumerico) 
            throw new Exception("Bad request", 400);
	}
	
	 try {
        if ($_SERVER["CONTENT_TYPE"] != "application/json")
            throw new Exception("Invalid content type", 415);

        validateParams();

        
        $ins = "INSERT INTO Prodotto (nome, prezzo, categoria, descrizione, immagine, codNegozio)
				VALUES ($nome, $prezzo, $categoria, $descrizione, $pathImmagine, $codNegozio);";  

        

        if (!($conn->query($ins)) 
            throw new Exception("Internal server error", 500);

        $conn->close();

        http_response_code(200);

               

    } catch (Exception $ex) {
        http_response_code($ex->getCode());
    }  

?>