<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Shows the result of a POST action</title>
</head>

<body>
<?php
	reset ($HTTP_POST_VARS);
	while (list ($key, $val) = each ($HTTP_POST_VARS)) {
	    echo "$key => $val<br>";
    }
?>
</body>
</html>
