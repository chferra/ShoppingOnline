<?php
	include_once(__DIR__ . '/../connector.php');
	
	function validateParams() {
		if (!isset($_GET["email"]) || !isset($_GET["password"]))
			return false;
		
		$email = $_GET["email"];
		$password = $_GET["password"];
		
		if (strlen($email) == 0 || strlen($password) == 0)
			throw new Exception("Invalid content type", 400);
	}
	
	try {
	
		validateParams();	

		$email = $_GET["email"];
		$password = md5($_GET["password"]);
		
		$sql = "SELECT * FROM utenti WHERE email='$email' AND password='$password'";
        $ris = $conn->query($sql);
        $conn->close();
		
		if (!$ris)
			throw new Exception("Internal server error", 500);
		
		$result = $ris ->fetch_array(MYSQLI_ASSOC);

		if ($ris->num_rows != 1) 
			throw new Exception("Invalid login", 401);
		
			
		$_SESSION["userID"] = $result["ID"];
		$_SESSION["name"] = $result["nome"];
		$_SESSION["negoziante"] = $result["negoziante"];

		//set session cookie
			
		http_response_code(200);
		$response = array("cookie" => "success");
		echo json_encode($response);		
	
	} catch (Exception $ex) {
        http_response_code($ex->getCode());
    }  
	
	
?>