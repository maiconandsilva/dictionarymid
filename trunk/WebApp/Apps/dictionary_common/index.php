<html>
  <head>
	<script type="text/javascript">
		"use strict";
		function redirectToHMI() {
			var userAcceptance=confirm("Dear User,\n\nYou will be charged at only once to download dictionary at flatrate\n\nDo you are to proceed ?");
			if (userAcceptance==true)
			  {
			window.location.href = "mini_hmi.php";
			  }
			window.close();
		}
		window.onload = redirectToHMI; 
    </script>
  </head>
  <body>
  </body>
</html>