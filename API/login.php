<?php
    include_once('connector.php');
	
	$previousPage = $_SERVER['HTTP_REFERER'];
	
	try {
		$jsonString = file_get_contents('php://input');
		$parameters = json_decode($json_str);
		
		$email = $obj->{'email'};
		$password = md5($obj->{'password'});
		
		$sql = "SELECT * FROM utenti WHERE email='$email' AND password='$password'";
        $ris = $conn->query($sql);
        $result = $ris ->fetch_array(MYSQLI_ASSOC);
		
        $conn->close();

        if ($ris->num_rows == 1) {
            //$_SESSION["username"] = $result["Username"];
            //$_SESSION["admin"] = $result["Admin"];
			//set session cookie
			
            header("Location: $previousPage");
        } else
            header("Location: login.php?erroreCredenziali=true");     
		
	} catch (Exception $e) {
		echo 'Fatal error: ',  $e->getMessage(), "\n";
	}

	
?>