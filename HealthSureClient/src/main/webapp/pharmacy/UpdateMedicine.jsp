<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
<head>
<title>Update Medicine</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/UpdateMedicine.css">
<style>
</style>
</head>
<body>
	<f:view>
		<jsp:include page="/navbar/NavPharmacy.jsp" />
		<div class="top">
			<h2>Update Medicine Details</h2>

			<div class="form-container">
				<h:form id="updateForm">

					<!-- Global feedback -->
					<h:messages globalOnly="true" layout="list" styleClass="message"
						infoClass="info" errorClass="error" />

					<!-- First Row -->
					<div class="form-row">
						<div class="form-group">
							<label class="form-label">Medicine Name <span
								style="color: red">*</span></label>
							<h:inputText id="medicineName"
								value="#{medicinesController.ejbMedicine.medicineName}"
								styleClass="form-input" />
							<h:message for="medicineName" styleClass="error" />
						</div>

						<div class="form-group">
							<label class="form-label">Description <span
								style="color: red">*</span></label>
							<h:inputText id="description"
								value="#{medicinesController.ejbMedicine.description}"
								styleClass="form-input" />
							<h:message for="description" styleClass="error" />
						</div>
					</div>

					<!-- Second Row -->
					<div class="form-row">
						<div class="form-group">
							<label class="form-label">Quantity In Stock <span
								style="color: red">*</span></label>
							<h:inputText id="quantity"
								value="#{medicinesController.ejbMedicine.quantityInStock}"
								styleClass="form-input" />
							<h:message for="quantity" styleClass="error" />
						</div>

						<div class="form-group">
							<label class="form-label">Unit Price <span
								style="color: red">*</span></label>
							<h:inputText id="unitPrice"
								value="#{medicinesController.ejbMedicine.unitPrice}"
								styleClass="form-input" />
							<h:message for="unitPrice" styleClass="error" />
						</div>
					</div>

					<!-- Third Row -->
					<div class="form-row">
						<div class="form-group">
							<label class="form-label">Purpose <span
								style="color: red">*</span></label>
							<h:inputText id="purpose"
								value="#{medicinesController.ejbMedicine.purpose}"
								styleClass="form-input" />
							<h:message for="purpose" styleClass="error" />
						</div>
					</div>
					<div class="center-container">
						<!-- Back Button -->
						<h:commandButton value="Back"
							action="#{medicinesController.redirectToView}" styleClass="btn"
							style="margin-right: 20px;" />

						<!-- Update Button  -->
						<h:commandButton value="Update Medicine"
							action="#{medicinesController.updateMedicineDetails}"
							styleClass="btn" />
					</div>
				</h:form>
			</div>
		</div>
	</f:view>

	<jsp:include page="/footer/Footer.jsp" />
</body>
</html>