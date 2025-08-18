<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
<head>
<meta charset="UTF-8">
<title>Registration Successful</title>
<!-- Google Font & Font Awesome -->

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/Sucess.css">


</head>
<body>
	<f:view>
		<div class="success-container">
			<i class="fas fa-user-check success-icon"></i>
			<h1>Registration Successful!</h1>
			<h:form>
				<h:outputText value="Your temporary  password is: " />
				<h:outputText value="#{controller.tempPass}"
					style="color:green; font-weight:bold" />
				<br />

				<h:outputText
					value=" Please use this password to log in using otp and reset your password during your first login." 
					style="color:#474141"/>

			</h:form>
			<p>Thank you for registration.</p>
			<a href="../home/Home.jsf" class="btn-home">Go to Home</a>

		</div>


	</f:view>
</body>
</html>
