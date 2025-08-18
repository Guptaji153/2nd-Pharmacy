<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<!DOCTYPE html>
<f:view>
<html>
<head>
<meta charset="UTF-8">
<title>Equipment Home Page</title>
<style>
    body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f3f8f6;
    margin: 0;
    padding: 0;
}
 
.container {
    padding: 40px 60px;
    max-width: 1200px;
    margin: 100px auto 40px auto;
}
 
h1 {
    color: #1f5d42;
    font-size: 36px;
    text-align: center;
    margin-bottom: 30px;
}
 
.intro {
    font-size: 17px;
    color: #444;
    line-height: 1.6;
    text-align: center;
    margin-bottom: 50px;
}
 
.button-group {
    display: flex;
    justify-content: center;
    gap: 30px;
    flex-wrap: wrap;
    margin-bottom: 40px;
}
 
.nav-button {
    padding: 14px 24px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-size: 16px;
    font-weight: 500;
    transition: background-color 0.3s ease, box-shadow 0.3s ease;
}
 
.nav-button:hover {
    background-color: #0056b3;
    box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}
 
.section {
    margin-top: 60px;
    background-color: #fff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
}
 
.section h2 {
    color: #155724;
    font-size: 22px;
    margin-bottom: 10px;
}
 
.section p {
    font-size: 15px;
    color: #555;
    line-height: 1.6;
}
 
.parent {
    margin-top: 40px;
    display: flex;
    justify-content: space-between;
    align-items: stretch;
    flex-wrap: nowrap; /* Force single row */
    gap: 20px;
}
 
.child1, .child2, .child3 {
    width: calc((100% - 40px) / 3); /* Three boxes with 20px gap between */
    height: 180px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    font-weight: 600;
    color: #155724;
    text-align: center;
    padding: 20px;
    transition: transform 0.3s ease;
    box-sizing: border-box;
}
 
 
.child1:hover, .child2:hover, .child3:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 12px rgba(0,0,0,0.12);
}
</style>
</head>
<body>
 
    <!-- Navbar -->
    <jsp:include page="/navbar/NavPharmacy.jsp" />
 
    <!-- Main Container -->
    <div class="container">
        <h1>Welcome to the Equipment Home Page</h1>
 
        <div class="intro">
            Manage your Equipment operations seamlessly. Here you can add new Equipments to inventory,
            dispense Equipments to patients, view available stock, and review sale history with ease.
        </div>
 
        <div class="button-group">
            <a href="AddEquipment.jsf" class="nav-button">➕ Add Equipment</a>
            <a href="SaleEquipments.jsf" class="nav-button">💊 Sale Equipment</a>
           <!--  <a href="ViewEquipments.jsf" class="nav-button">📋 Show Equipments</a>
            <a href="Search&ViewEquipment.jsf" class="nav-button">🔍 Search Sold Details</a>    -->
            
            <h:form>
		        <h:commandButton value="View & Update Equipments" action="#{equipmentController.ViewPharmacyStocks}"
		            styleClass="nav-button" />
		    </h:form>
        </div>
        

        
        
        <div class="section">
            <h2>About This Page</h2>
            <p>
                This Page is designed to make Equipment tracking and sales more organized and accessible.
                From adding new inventory to tracking patient medicine records, everything is managed in one place.
                The interface is built with JSF and integrates seamlessly with your backend database.
            </p>
        </div>
    </div>
	<jsp:include page="/footer/Footer.jsp" />
</body>
</html>
</f:view>