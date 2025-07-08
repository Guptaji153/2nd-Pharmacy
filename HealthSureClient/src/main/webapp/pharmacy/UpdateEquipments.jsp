<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Update Medicine</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
        }
        h2 {
            text-align: center;
            color: #007bff;
        }
        .form-container {
            width: 50%;
            margin: auto;
            background-color: #ffffff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
        }
        .form-container table {
            width: 100%;
        }
        .form-container td {
            padding: 10px;
        }
        .form-container input[type="text"],
        .form-container input[type="number"],
        .form-container input[type="date"] {
            width: 95%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container .btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-container .btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<f:view>
    <h2>Update Medicine Details</h2>

    <div class="form-container">
        <h:form>
            <h:messages globalOnly="true" style="color:red;" />

            <h:panelGrid columns="2" cellpadding="5">
                <h:outputLabel for="medicineName" value="Medicine Name:" />
                <h:inputText id="medicineName" value="#{ejbEquipmentController.ejbEquipment.equipmentName}" required="true" />

                <h:outputLabel for="description" value="Description:" />
                <h:inputText id="description" value="#{ejbEquipmentController.ejbEquipment.description}" required="true" />

                <h:outputLabel for="quantity" value="Quantity In Stock:" />
                <h:inputText id="quantity" value="#{ejbEquipmentController.ejbEquipment.quantity}" required="true" />

                <h:outputLabel for="unitPrice" value="Unit Price:" />
                <h:inputText id="unitPrice" value="#{ejbEquipmentController.ejbEquipment.unitPrice}" required="true" />

                <h:outputLabel for="purpose" value="Purpose:" />
                <h:inputText id="purpose" value="#{ejbEquipmentController.ejbEquipment.status}" required="true" />
            </h:panelGrid>

            <br/>
            <h:commandButton value="Update Medicine"
                             action="#{ejbEquipmentController.updateEquipmentDetails}"
                             styleClass="btn" />
        </h:form>
    </div>
</f:view>
</body>
</html>