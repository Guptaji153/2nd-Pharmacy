<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<!DOCTYPE html>
	<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset Password</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/ValidatePass.css">

</head>
<body>
	<jsp:include page="/navbar/NavAuthentication.jsp" />
	<div class="top">
		<h:form id="form">
			<div class="form-container">

				<h2>Reset Your Password</h2>

				<!-- Email input -->
				
				<div class="form-group">
					<h:outputLabel for="email" value="Email Id:"
						styleClass="form-label" />
					<h:inputText id="email" value="#{sessionScope.otpEmail}"
					  readonly="true"
					 styleClass="form-input" />
				</div>
				
				<!--   Temperary Password  -->
				<div class="form-group">
					<h:outputLabel for="TemperaryPassword" value="Temperary Password:"
						styleClass="form-label" />
					<h:inputText id="TemperaryPassword" value="#{controller.temporaryPassword}"
					  
					 styleClass="form-input" />
				</div>

				<!-- New Password -->
				<div class="form-group">
					<h:outputLabel for="newPassword" value="New Password:"
						styleClass="form-label" />
					<h:inputSecret id="newPassword"
						value="#{controller.pharmacy.password}" styleClass="form-input" />
					<h:message for="pwd" style="color:red" />

					<!-- Confirm New Password -->
					<div class="form-group">
						<h:outputLabel for="confirmPassword" value="Confirm Password:"
							styleClass="form-label" />
						<h:inputSecret id="confirmPassword"
							value="#{controller.confirmPassword}" styleClass="form-input" />
						<h:message for="pwd" style="color:red" />
					</div>

					<h:messages globalOnly="true" style="color:red;" />


					<!-- Submit Button -->
					<div class="form-group">
						<h:commandButton value="Update Password"
							action="#{controller.updatePasswordByOtp}"
							styleClass="submit-button" />
					</div>

					<!-- Global error message display 
            <h:messages globalOnly="true" style="color:red;" />  -->

				</div>
			</div>
		</h:form>
	</div>
	<jsp:include page="/footer/Footer.jsp" />
</body>
	</html>
</f:view>
