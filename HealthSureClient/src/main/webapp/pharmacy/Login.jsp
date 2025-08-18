<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>


<html>
<head>
<title>Pharmacy Login</title>
<script>
	window.addEventListener("pageshow", function(event) {
		if (event.persisted) {
			
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
					 
                      <h:commandLink action="#{controller.loginWithOtp }" value="Login with OTP" styleClass="reset" /> 
                      <h:commandLink action="#{controller.resetPassword }" value="Forgot password" styleClass="reset" /> 
					    
					</div>
				</div>
				
			</h:form>
		</f:view>
	</div>
	<jsp:include page="/footer/Footer.jsp" />

	
</body>
</html>
