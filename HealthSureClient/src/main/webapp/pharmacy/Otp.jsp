<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Verify OTP</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/Otp.css" />
</head>
<body>
	<jsp:include page="/navbar/NavAuthentication.jsp" />
	<div class="top">
		<f:view>
			<h:form>
				<div class="form-container">
					<!-- glogal error message -->
					<h:messages globalOnly="true" layout="table" style="" />
					<p id="timerDisplay"
						style="font-weight: bold; font-size: 16px; color: #333;">OTP
						valid for: 2 minutes</p>

					<!-- pharmacy email from session map -->
					<div class="form-group">
						<h:outputLabel for="email" value="Email ID:"
							styleClass="form-label" />
						<h:inputText id="email" value="#{sessionScope.otpemail}"
							readonly="true" styleClass="form-input" />
					</div>
					<!-- OTP code -->
					<div class="form-group">
						<h:outputLabel for="otp" value="OTP:" styleClass="form-label" />
						<h:inputText id="otp" value="#{pharmacyOtp.otpCode}"
							styleClass="form-input" />
					</div>
					<!-- OTP submit/validate button -->
					<div class="form-group">
						<h:commandButton value="Submit OTP"
							action="#{controller.generatePassword(controller.pharmacy.email, pharmacyOtp.otpCode)}"
							styleClass="submit-button" />
					</div>
					<!-- Resend OTP button -->
					<div class="form-group">
						<h:commandButton value="Resend OTP"
							action="#{controller.resendOtp}" immediate="true"
							styleClass="submit-button" />
					</div>
				</div>
			</h:form>
		</f:view>
	</div>
	<jsp:include page="/footer/Footer.jsp" />

</body>
</html>
