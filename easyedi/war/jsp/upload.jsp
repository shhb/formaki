<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Upload you</title>
	<script type="text/javascript" src="../jquery/jquery-1.7.2.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/mine.css" />
	<script type="text/javascript"> 
		$(document).ready(function(){
			$(".flip").click(function(){
	    		$(".panel").slideToggle("slow");
	  		});
			$(".ex .hide").click(function(){
		    	$(this).parents(".ex").hide("slow");
			});
			
			/* $(".feedbackPopup").fancybox({
	             'width'             : '38%',
	             'height'            : '65%',
	             'min-height'        : 180,
	             'autoScale'     : false,
	             'transitionIn'      : 'none',
	             'transitionOut'     : 'none',
	             'hideOnOverlayClick' : false,
	             'type'          : 'iframe'
	         });

	      	$(".registerPopup").fancybox({
	             'width'         : '40%',
	             'height'        : '70%',
	             'min-height'        : 180,
	             'autoScale'     : false,
	             'transitionIn'      : 'none',
	             'transitionOut'     : 'none',
	             'hideOnOverlayClick' : false,
	             'type'              : 'iframe'
	         }); */
		});
		
	</script>
</head>
<body>
<!-- Three portion in page are exict -->
<!-- First Part is for TitleBar -->
<div class="titleBar">Welcome to easy EDI</div>

<div class="ex">
	<img src="../image/icon/close_icon.gif" class="hide">
	<form action="../ad" class="ad">
	<p class="adText">[AD] .....</p>
		
	</form>
</div>

<div class="childBar">
		<table>
		<tr><td><img src="../image/icon/collapse_icon.gif" class="flip"></td>
		<td><div class="ordinaryText">File Upload Section</div></td></tr></table>
		
</div>

<div class="panel">
	<form action="../upload" enctype='multipart/form-data' method="post">

	    <fieldset title="SELECT FILE" class="box" width="100px">
		    <table>
		    <tr>
			    <td>
			    	<div class="fileinputs">
			    		
						<input type="file" name="realFile" id="realFile" onchange="javascript:this.form.showfile.value = this.value;" />
			    		<div class="fakefile">
							<input name="showfile" class="fileInput" type="text" />
						</div>
					</div>
			    </td>
		    	<td>
		    		<img src="../image/button_select.gif" onclick="$('#realFile').click();" class="image" />
		    	</td>
		    	<td>
		    		<input type="image" id="uploadFile" src="../image/icon/filepload_icon.jpg" value="submit" alt="Submit">
		    	</td>
		    </tr>
		    </table>	        
	    </fieldset>
	    <div class="box adText">FileContent</div>
	<div><textarea rows="15" cols="150"></textarea></div>
	</form>
</div>
</body>
</html>