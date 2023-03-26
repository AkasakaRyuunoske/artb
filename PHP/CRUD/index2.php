<?php
	echo date('y/m/d ♥♥♥ h: i: s') . "<br>";
	$Anata = $_GET['Anata'];
	$Wa = $_GET['Wa'];
	$Nee = $_GET['Nee'];
	$methodType = $_GET['MethodType'];

	$servername = "localhost";
	$db_user = "root";
	$db_pass = "";
	$db_name = "Harakiri";

	$query = null;


	echo $Anata . "<br>";
	echo $Wa . "<br>";
	echo $Nee . "<br>";
	echo $methodType . "<br>";

	$conn = new mysqli($servername, $db_user, $db_pass, $db_name);
	if ($conn->connect_error) {
	  die("Connection failed: " . $conn->connect_error);
	}else{
		echo "Connected Succesful";
	}

	if ($methodType == "Insert into") {

		echo "<br>". $methodType . " must be insert";
			$query = "INSERT INTO Yume(Nee, Anata, Wa) VALUES('". $Nee . "', '". $Anata . "', '" . $Wa . "');";
		echo "<br>" . $query;

	}elseif ($methodType == "Delete") {

		echo "<br>".$methodType . " must be Delete";
		$query = "DELETE FROM Yume WHERE Nee ='". $Nee . "' AND Anata = '". $Anata . "' AND Wa ='" . $Wa . "';";
		echo "<br>" . $query;

	}elseif ($methodType == "Update") {

		echo "<br>".$methodType . " must be Update";
		$query = "UPDATE Yume SET Nee = '". $Nee . "', Anata = '". $Anata . "', Wa ='" . $Wa . "'
		WHERE Itsuka =". 2 . ";";
		echo "<br>" . $query;
	
	}elseif ($methodType == "Select") {
			echo "<br>".$methodType . " must be Select";
			$query = "Select * from Yume;";
			$resultSet = mysqli_query($conn, $query) or die(mysql_error());
			print $query;

			while($row = mysqli_fetch_assoc($resultSet)){
	    	foreach($row as $cname => $cvalue){
	        print "$cname: $cvalue\t";
    	}
	    print "\r\n";
	}
}

	$conn->query($query);
?>
