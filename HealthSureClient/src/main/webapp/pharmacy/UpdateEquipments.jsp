<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Update Medicine</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/UpdateMedicine.css">
</head>
<body>

<f:view>
<jsp:include page="/navbar/NavPharmacy.jsp" />
<div class ="top">
    <h2>Update Medicine Details</h2>

    <div class="form-container">
        <h:form id="err">

            <!-- Global feedback -->
            <h:messages globalOnly="true" layout="list" styleClass="message" infoClass="info" errorClass="error" />

            <!-- First Row -->
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">Equipment Name <span style="color:red">*</span></label>
                    <h:inputText id="equipmentName" value="#{ejbEquipmentController.ejbEquipment.equipmentName}" styleClass="form-input" />
                    <h:message for="equipmentName" styleClass="error" />
                </div>

                <div class="form-group">
                    <label class="form-label">Description <span style="color:red">*</span></label>
                    <h:inputText id="description" value="#{ejbEquipmentController.ejbEquipment.description}" styleClass="form-input" />
                    <h:message for="description" styleClass="error" />
                </div>
            </div>

            <!-- Second Row -->
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">Quantity In Stock <span style="color:red">*</span></label>
                    <h:inputText id="quantity" value="#{ejbEquipmentController.ejbEquipment.quantity}" styleClass="form-input" />
                    <h:message for="quantity" styleClass="error" />
                </div>

                <div class="form-group">
                    <label class="form-label">Unit Price <span style="color:red">*</span></label>
                    <h:inputText id="unitPrice" value="#{ejbEquipmentController.ejbEquipment.unitPrice}" styleClass="form-input" />
                    <h:message for="unitPrice" styleClass="error" />
                </div>
            </div>

            <!-- Third Row -->
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">Status <span style="color:red">*</span></label>
                    <h:inputText id="status" value="#{ejbEquipmentController.ejbEquipment.status}" styleClass="form-input" />
                    <h:message for="status" styleClass="error" />
                </div>
            </div>
			
			<div class="center-container">
			
            <h:commandButton value="Update Medicine"
                             action="#{ejbEquipmentController.updateEquipmentDetails}"
                             styleClass="btn" />
			</div>
        </h:form>
    </div>
    </div>
</f:view>
<jsp:include page="/footer/Footer.jsp"/>

</body>
</html>