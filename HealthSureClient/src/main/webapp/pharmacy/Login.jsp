<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>


<html>
<head>
<title>Pharmacy Login</title>
<script>
	window.addEventListener("pageshow", function(event) {
		if (event.persisted) {
			// Page was restored from the bfcache (back-forward cache)
			document.getElementById("form:email").value = "";
			document.getElementById("form:password").value = "";
		}
	});
</script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/Pharmacylogin.css">
</head>
<body>
	<jsp:include page="/navbar/NavAuthentication.jsp" />
	<div class="top">
		<f:view>
			<h:form id="form">
				<div class="form-container">
					<h2>Login</h2>
					<!--        <h:messages globalOnly="true" />
<h:outputText value="#{flash.message}" />  -->

					<!-- printing global error message -->
					<h:messages globalOnly="true" layout="table"
						styleClass="error-message" />

					<!-- pharmacy email -->
					<div class="form-group">
						<h:outputLabel for="email" value="Email:" styleClass="form-label" />
						<h:inputText id="email" value="#{controller.pharmacy.email}"
							autocomplete="off" styleClass="form-input" />
						<h:message for="email" style="color:red" />
					</div>
					<!-- pharmacy password  -->
					<div class="form-group">
						<h:outputLabel for="password" value="Password:"
							styleClass="form-label" />
						<h:inputSecret id="password"
							value="#{controller.pharmacy.password}" autocomplete="off"
							styleClass="form-input" />
						<h:message for="password" style="color:red" />
					</div>
					<!-- login button -->
					<div class="form-group">
						<h:commandButton value="Login"
							action="#{controller.validateLogin}" styleClass="submit-button" />
					</div>
					<div class="foot-div">
					    <a href="resetPassword.jsf" class="reset"> Reset your password </a>
					    <a href="LoginWithOtp.jsf" class="otpLogin">Login with OTP </a>
					</div>
				</div>
				
			</h:form>
		</f:view>
	</div>
	<jsp:include page="/footer/Footer.jsp" />

	
</body>
</html>
