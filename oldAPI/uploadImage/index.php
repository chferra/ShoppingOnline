<?php
    include_once(__DIR__ . '/../connector.php');
    
    $jsonString = file_get_contents('php://input');
    $parameters = json_decode($jsonString);
    
    $percorso = $parameters->{'uploadImage'}->{'percorso'};
	$codNegozio = $parameters->{'uploadImage'}->{'codNegozio'};
	
	//$codProdotto = $parameters->{'uploadImage'}->{'codProdotto'};
	  
	function validateParams() {
        global $percorso;
		global $codNegozio;
   
        if (!isset($percorso) || !isset($codNegozio))
            throw new Exception("Non acceptable", 406);	
		
		$IdNumerico = is_numeric($codNegozio);
        
        if (!$IdNumerico) 
            throw new Exception("Bad request", 400);		
    }

    try {
        if ($_SERVER["CONTENT_TYPE"] != "application/json")
            throw new Exception("Invalid content type", 415);

        validateParams();
        
        $ins = "INSERT INTO fotografie(percorso, CodNegozio) 
                    VALUES ($percorso, $codNegozio)";  
        

        if (!($conn->query($ins)) 
            throw new Exception("Internal server error", 500);

        http_response_code(200);               

    } catch (Exception $ex) {
        http_response_code($ex->getCode());
    }  
	
?>