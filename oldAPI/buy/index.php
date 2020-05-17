<?php
    include_once(__DIR__ . '/../connector.php');
    
    $jsonString = file_get_contents('php://input');
    $parameters = json_decode($jsonString);
    
    $IdUtente = $parameters->{'lancioOrdine'}->{'IdUtente'};
    $IdIndirizzo = $parameters->{'lancioOrdine'}->{'IdIndirizzo'};
    $IdCarrello = $parameters->{'lancioOrdine'}->{'IdCarrello'};
    $fattura = $parameters->{'lancioOrdine'}->{'fattura'};
    $consegnaDomicilio = $parameters->{'lancioOrdine'}->{'consegnaDomicilio'};
    
	function validateParams() {
        global $IdUtente;
        global $IdIndirizzo;
        global $IdCarrello;
        global $fattura;
        global $consegnaDomicilio;

        if (!isset($IdUtente) || !isset($IdIndirizzo) || !isset($IdCarrello) || !isset($fattura) || !isset($consegnaDomicilio))
            return false;

        $IdNumerici = is_numeric($IdUtente) && is_numeric($IdIndirizzo) && is_numeric($IdCarrello);
        $booleaniValidi = is_bool($fattura) && is_bool($consegnaDomicilio);

        if (!($IdNumerici && $booleaniValidi)) 
            throw new Exception("Bad request", 400);
    }

    try {
        if ($_SERVER["CONTENT_TYPE"] != "application/json")
            throw new Exception("Invalid content type", 415);

        validateParams();

        $sql = "SELECT ordineEffettuato, IdNegozio FROM carrelli WHERE ID=$IdCarrello";
        $ris = $conn->query($sql);

        if ($ris->num_rows == 0) 
            throw new Exception("Not found", 404);

        $result = $ris->fetch_array(MYSQLI_ASSOC);
        $ordineEffettuato = $result["ordineEffettuato"];
        $IdNegozio = $result["IdNegozio"];
        
        if ($ordineEffettuato == "1") 
            throw new Exception("Cart already submitted", 400);

        $ins = "INSERT INTO Ordini(consegnaDomicilio, fattura, IdUtente, IdCarrello, IdIndirizzoConsegna, IdNegozio) 
                    VALUES ($consegnaDomicilio, $fattura, $IdUtente, $IdCarrello, $IdIndirizzo, $IdNegozio)";  

        $up = "UPDATE Carrelli SET ordineEffettuato = '1' WHERE ID = $IdCarrello";

        if ($conn->query($up) && !($conn->query($ins)) 
            throw new Exception("Internal server error", 500);

        http_response_code(200);

        $response = array("IdOrdine" => $conn->insert_id);                    
        echo json_encode($response);        

    } catch (Exception $ex) {
        http_response_code($ex->getCode());
    }  
	
?>