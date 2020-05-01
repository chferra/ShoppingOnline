<?php
    include_once(__DIR__ . '/../connector.php');
    
    $jsonString = file_get_contents('php://input');
    $parameters = json_decode($jsonString);
    
    $IdUtente = $parameters->{'lancioOrdine'}->{'IdUtente'};
    $IdIndirizzo = $parameters->{'lancioOrdine'}->{'IdIndirizzo'};
    $IdCarrello = $parameters->{'lancioOrdine'}->{'IdCarrello'};
    $dataOraConsegna = $parameters->{'lancioOrdine'}->{'dataOraConsegna'};
    $fattura = $parameters->{'lancioOrdine'}->{'fattura'};
    $consegnaDomicilio = $parameters->{'lancioOrdine'}->{'consegnaDomicilio'};
    
	function validateParams() {
        global $IdUtente;
        global $IdIndirizzo;
        global $IdCarrello;
        global $dataOraConsegna;
        global $fattura;
        global $consegnaDomicilio;

        if (!isset($IdUtente) || !isset($IdIndirizzo) || !isset($IdCarrello) || !isset($dataOraConsegna) || !isset($fattura) || !isset($consegnaDomicilio))
            return false;

        $IdNumerici = is_numeric($IdUtente) && is_numeric($IdIndirizzo) && is_numeric($IdCarrello);
        $dataOraValida = strtotime($dataOraConsegna) > time();
        $booleaniValidi = is_bool($fattura) && is_bool($consegnaDomicilio);

        return $IdNumerici && $dataOraValida && $booleaniValidi;
    }
    
    if ($_SERVER["CONTENT_TYPE"] == "application/json") {
	
	    if (validateParams()) {       
        
            $sql = "SELECT ordineEffettuato, IdNegozio FROM carrelli WHERE ID=$IdCarrello";
            $ris = $conn->query($sql);

            if ($ris->num_rows == 1) {
            $result = $ris->fetch_array(MYSQLI_ASSOC);
            $ordineEffettuato = $result["ordineEffettuato"];
            $IdNegozio = $result["IdNegozio"];
        
            if ($ordineEffettuato == "0") {
                $ins = "INSERT INTO Ordini('dataOraConsegna', 'consegnaDomicilio', 'fattura', 'IdUtente', 'IdCarrello', 'IdIndirizzo', 'IdNegozio') 
                        VALUES ($dataOraConsegna, $consegnaDomicilio, $fattura, $IdUtente, $IdCarrello, $IdIndirizzo, $IdNegozio)";                

                $up = "UPDATE Carrelli SET ordineEffettuato = '1' WHERE ID = $IdCarrello";
                $getIdOrdine = "SELECT LAST ID FROM Ordini";

                if ($conn->query($ins) && $conn->query($up) && $ris = $conn->query($getIdOrdine)) {
                    http_response_code(200);
                    $response = array("IdOrdine" => $ris->fetch_array(MYSQLI_ASSOC)["ID"]);
                    
                    echo json_encode($response);
                }
                else
                    http_response_code(500);               

                $conn->close();
            } else
                http_response_code(4xx); 	//da 452
        } else
            http_response_code(404);		    
	    } else 
            http_response_code(400);
    } else
        http_response_code(415);
	
?>