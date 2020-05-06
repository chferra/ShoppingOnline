<?php
	
	include_once(__DIR__ . '/../connector.php');
	
	$jsonString = file_get_contents('php://input');
    $parameters = json_decode($jsonString);
	
	$codNegozio = $parameters->{'uploadImage'}->{'codNegozio'};
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
		
		if (!isset($codNegozio) || !isset($nome) || !isset($prezzo) || !isset($categoria) || !isset($descrizione) || !isset($pathImmagine)) {
            return false;
		}
		
		$prezzoNumerico = is_numeric($prezzo);
		$codNumerico = is_numeric($codNegozio);
        
        if (!$prezzoNumerico || !$codNumerico) 
            throw new Exception("Bad request", 400);
	}
	
	 try {
        if ($_SERVER["CONTENT_TYPE"] != "application/json")
            throw new Exception("Invalid content type", 415);

        validateParams();
        
        $ins = "INSERT INTO Prodotti (nome, codNegozio, prezzo, categoria, descrizione, immagine )
				VALUES ($nome, $codNegozio, $prezzo, $categoria, $descrizione, $pathImmagine);)";  

        if (!($conn->query($ins)) 
            throw new Exception("Internal server error", 500);

        http_response_code(200);

		$response = array("IdProdotto" => $conn->insert_id);                    
        echo json_encode($response); 		

    } catch (Exception $ex) {
        http_response_code($ex->getCode());
    }  

?>