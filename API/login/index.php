<?php
	include_once(__DIR__ . '/../connector.php');
	
	function validateParams() {
		if (strlen($_GET["email"]) > 32 || strlen($_GET["password"]) > 32)
			return false;
		else if (filter_var($_GET["email"], FILTER_VALIDATE_EMAIL))
			return true;		
	}
	
	//$previousPage = $_SERVER['HTTP_REFERER'];
	
	if (isset($_GET["email"]) && isset($_GET["password"]) && validateParams()) {
		//$jsonString = file_get_contents('php://input');
		//$parameters = json_decode($json_str);
		
		//$email = $obj->{'email'};
		//$password = md5($obj->{'password'});
		$email = $_GET["email"];
		$password = $_GET["password"];
		
		$sql = "SELECT * FROM utenti WHERE email='$email' AND password='$password'";
        $ris = $conn->query($sql);
        $result = $ris ->fetch_array(MYSQLI_ASSOC);
		
		$conn->close();

        if ($ris->num_rows == 1) {
			
			$_SESSION["userID"] = $result["ID"];
			$_SESSION["name"] = $result["nome"];
			$_SESSION["negoziante"] = $result["negoziante"];

			//set session cookie
			
			http_response_code(200);
			$response = array("cookie" => "success");
			echo json_encode($response);
		} else 
			http_response_code(401);
	} else 
		http_response_code(406);	

	
	
?>