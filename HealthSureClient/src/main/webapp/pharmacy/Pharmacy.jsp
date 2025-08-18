<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
 
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Pharmacy Home</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 100px 0 0 0;;
            background-color: #f9f9f9;
            color: #333;
        }
 
        .wrapper, .banner {
            width: 100%;
            overflow: hidden;
        }
 
        .banner img {
            width: 100%;
            height: 100vh;
            display: block;
        }
 
        .welcome {
            background-color: #ffffff;
            padding: 40px 20px;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
 
        .welcome h2 {
            color: #2c3e50;
            font-size: 28px;
            margin-bottom: 10px;
        }
 
        .welcome p {
            font-size: 18px;
            color: #555;
        }
 
        .services {
            background-color: #eaf2f8;
            padding: 40px 20px;
            text-align: center;
        }
 
        .services h3 {
            font-size: 24px;
            color: #21618c;
            margin-bottom: 30px;
        }
 
        .services ul {
            list-style: none;
            padding: 0;
            display: flex;
            justify-content: center;
            gap: 30px;
            flex-wrap: wrap;
        }
 
        .services li {
            background-color: #ffffff;
            width: 260px;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.1);
            text-align: center;
            font-size: 16px;
            display: flex;
    		flex-direction: column;
    		align-items: center;
    		justify-content: flex-start;
        }
 
        .services li img {
            width: 100px;
            height: auto;
            margin-bottom: 10px;
        }
    </style>
</head>
 
<body>
<f:view>
    <jsp:include page="/navbar/NavPharmacy.jsp" />
 
    <div class="wrapper"></div>
    <div class="banner">
        <img src="/HealthSureClient/resources/media/images/banner.jpg" alt="Pharmacy Banner" />
    </div>
 
    <section class="welcome">
        <h2>
        	<h:outputText value="Welcome to #{sessionScope.pharmacy_name }"/>
        </h2>
        <p>Your trusted partner in health and wellness. Browse our products, explore resources, and get expert advice—all in one place.</p>
    </section>
 
    <section class="services">
        <h3>Our Services</h3>
        <ul>
            <li><img src="/HealthSureClient/resources/media/images/medicines.jpg" alt="Medicines" /> Wide range of medicines</li>
            <li><img src="/HealthSureClient/resources/media/images/consultation.jpg" alt="Consultation" /> Free pharmacist consultation</li>
            <li><img src="/HealthSureClient/resources/media/images/delivery.jpg" alt="Delivery" /> Fast and safe home delivery</li>
        </ul>
    </section>
</f:view>
 
<jsp:include page="/footer/Footer.jsp" />
</body>
</html>