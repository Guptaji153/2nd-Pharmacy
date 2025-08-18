<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login via OTP</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/otp.css" />


</head>
<body>

	<jsp:include page="/navbar/NavAuthentication.jsp" />

	<div class="top">
		<f:view>
			<h:form id="otpForm">
				<div class="form-container">

					<!-- Global messages -->
					<h:messages globalOnly="true" layout="table" style="color:red;" />

					<!-- Email Field -->
					<div class="form-group">
						<h:outputLabel for="email" value="Email ID:"
							styleClass="form-label" />
						<h:inputText id="email" value="#{controller.loginEmail}"
							styleClass="form-input"  />
						<h:message for="email" style="color:red;" />
					</div>

					<!-- Send OTP Button -->
					<div class="form-group">
						<h:commandButton value="Send OTP"
							action="#{controller.sendLoginOtp}" styleClass="submit-button"
							disabled="#{controller.sendOtpDisabled }" />
					</div>

					<!-- Timer display -->
					<p id="timerDisplay"
						style="font-weight: bold; font-size: 16px; color: #333;">OTP
						valid for: 2 minutes</p>

					<!-- OTP Field -->
					<div class="form-group">
						<h:outputLabel for="otp" value="Enter OTP:"
							styleClass="form-label" />
						<h:inputText id="otp" value="#{controller.loginOtp}"
							styleClass="form-input"  />
						<h:message for="otp" style="color:red;" />
					</div>

					<!-- Submit OTP Button  -->
					<div class="form-group">
						<h:commandButton value="Submit OTP"
							action="#{controller.verifyLoginOtp}" styleClass="submit-button"
							 />
					</div>

					<!-- Resend OTP --> 
					<div class="form-group">
						<h:commandButton value="Resend OTP"
							action="#{controller.resendLoginOtp}" styleClass="submit-button" 
							
							/>
					</div>

				</div>
			</h:form>
		</f:view>
	</div>

	<jsp:include page="/footer/Footer.jsp" />
</body>
</html>