<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:view>
<html>
<head>
    <meta charset="UTF-8">
    <title>Owner Registration</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/AddPharmacy.css">
    
</head>
<body>
<jsp:include page="/navbar/NavAuthentication.jsp" />
<div class ="top">
    <h:form id="form" styleClass="form-container">
    <!-- span tag for information -->
        <p style="font-size: 12px; color: #777;">
            Fields marked with <span style="color:red">*</span> are mandatory.
        </p>
        <h2>Owner Details</h2>
		<!-- First row -->
        <div class="form-row">
        <!-- First name -->
            <div class="form-group">
                <label class="form-label"><span style="color: red">*</span>First Name </label>
                <h:inputText id="firstName" value="#{pharmacy.firstName}" styleClass="form-input" />
                <h:message for="firstName" style="color:red" />
            </div>
            <!-- Middle name -->
            <div class="form-group">
                <label class="form-label">Middle Name</label>
                <h:inputText id="middleName" value="#{pharmacy.middleName}" styleClass="form-input" />
            </div>
        </div>
		<!-- Second row -->
        <div class="form-row">
            <!-- Last name -->
            <div class="form-group">
                <label class="form-label"><span style="color: red">*</span>Last Name </label>
                <h:inputText id="lastName" value="#{pharmacy.lastName}" styleClass="form-input" />
                <h:message for="lastName" style="color:red" />
            </div>
            <!-- Gender -->
            <div class="form-group">
                <label class="form-label"><span style="color: red">*</span>Gender </label>
                <h:selectOneMenu id="gender" value="#{pharmacy.gender}" styleClass="form-input">
                    <f:selectItem itemLabel="Select" itemValue="" />
                    <f:selectItem itemLabel="Male" itemValue="Male" />
                    <f:selectItem itemLabel="Female" itemValue="Female" />
                    <f:selectItem itemLabel="Other" itemValue="Other" />
                </h:selectOneMenu>
                <h:message for="gender" style="color:red" />
            </div>
        </div>
		<!-- Third Row -->
        <div class="form-row">
            <!-- Owner Mobile Number -->
            <div class="form-group">
                <label class="form-label"> <span style="color: red">*</span>Mobile Number</label>
                <h:inputText id="ownerMobile" value="#{pharmacy.ownerMobile}" styleClass="form-input" />
                <h:message for="ownerMobile" style="color:red" />
            </div>
            <!-- Owner email id -->
            <div class="form-group">
                <label class="form-label"> <span style="color: red">*</span>Email</label>
                <h:inputText id="ownerEmail" value="#{pharmacy.ownerEmail}" styleClass="form-input" />
                <h:message for="ownerEmail" style="color:red" />
            </div>
            
            
        </div>
		<!-- Fourth row -->
        <div class="form-row">
            <!-- Owner aadhar no -->
            <div class="form-group">
                <label class="form-label"> <span style="color: red">*</span>Aadhar Number</label>
                <h:inputText id="aadharNo" value="#{pharmacy.aadhar}" styleClass="form-input" />
                <h:message for="aadharNo" style="color:red" />
            </div>
            <!-- Owner address -->
            <div class="form-group">
                <label class="form-label"><span style="color: red">*</span>Owner Address</label>
                <h:inputText id="ownerAddress" value="#{pharmacy.ownerAddress}" styleClass="form-input" />
                <h:message for="ownerAddress" style="color:red" />
            </div>
        </div>
		<!-- Submit button -->
        <h:commandButton value="Next" 
                     action="#{controller.proceedToPharmacyDetails}" 
                     styleClass="submit-button" />
		<h:commandButton value = "reset" action="#{controller.refreshButton }" styleClass="submit-button" />
		
    </h:form>
  </div>
    <jsp:include page="/footer/Footer.jsp" />

</body>
</html>
</f:view>
