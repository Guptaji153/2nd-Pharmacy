<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Terms and Conditions - Pharmacy Registration</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/Terms&Conditions.css">

</head>
<body>
	<jsp:include page="/navbar/NavAuthentication.jsp" />
	<div class="top">
	
		<main>
			
			<h1>Terms and Conditions for Pharmacy Registration</h1>
			<p>These terms and conditions govern the functional, regulatory,
				and contractual requirements for operating a pharmacy within a
				hospital system. They are based on standard operating procedures
				across accredited hospitals.</p>

			<h2>1. Regulatory Compliance</h2>
			<ul>
				<li>All statutory licenses (e.g., Drug License, GST, etc.) must
					be valid and displayed prominently.</li>
				<li>Pharmacists must be registered with State Pharmacy Council
					and comply with the Pharmacy Act, 1948.</li>
				<li>Must follow all applicable guidelines under Drugs and
					Cosmetics Act and Medical Council regulations.</li>
			</ul>

			<h2>2. Operational Conditions</h2>
			<ul>
				<li>Only valid prescriptions from hospital-authorized providers
					may be used to dispense medicine.</li>
				<li>Drugs with expired or low shelf life (less than 6 months)
					are not allowed to be sold.</li>
				<li>Out-of-stock medicines must be arranged within 24 hours or
					substituted after physician consultation.</li>
			</ul>

			<h2>3. Service Hours and Availability</h2>
			<ul>
				<li>Operational hours should be at least 8:00 AM to 8:00 PM;
					24x7 in emergency departments.</li>
				<li>Essential medicines must be available at all times
					including weekends and holidays.</li>
				<li>Emergency medicine supply must be guaranteed
					round-the-clock when applicable.</li>
			</ul>

			<h2>4. Stock and Inventory Management</h2>
			<ul>
				<li>Proper records for stock in/out and expiry must be
					digitally maintained.</li>
				<li>Expired, counterfeit, or unapproved medicines will result
					in serious penalties or de-registration.</li>
			</ul>

			<h2>5. Pricing and Billing</h2>
			<ul>
				<li>Medicine pricing must comply with Government Drug Price
					Control Orders (DPCO), MRP norms.</li>
				<li>Discounts to patients (if any) must be transparent and
					approved by hospital finance authority.</li>
				<li>Proper GST invoices must be issued and reconciled with the
					hospital management system where applicable.</li>
			</ul>

			<h2>6. Staff and Conduct</h2>
			<ul>
				<li>All pharmacy staff must behave professionally and wear
					official identification badges.</li>
				<li>Rude or unethical behavior with patients, attendants, or
					staff may result in license revocation.</li>
				<li>All statutory obligations such as PF, ESI, wages, etc.,
					must be strictly followed for employees.</li>
			</ul>

			<h2>7. Infrastructure and IT System</h2>
			<ul>
				<li>The pharmacy shall be responsible for furnishing, computer
					systems, printers, barcoding devices, etc.</li>
				<li>Cold chain, air-conditioning, and safety systems must be
					available and functional at all times.</li>
				<li>The layout should comply with fire safety and public health
					code standards.</li>
			</ul>
		
			<h2>8. Quality Assurance and Audit</h2>
			<ul>
				<li>The pharmacy must implement self-audit mechanisms and
					maintain medicine quality checks.</li>
				<li>The hospital management may conduct audits anytime without
					prior notice.</li>
			</ul>

			<h2>9. Confidentiality & Data Protection</h2>
			<ul>
				<li>Patient records, prescriptions, and billing data must be
					treated as confidential.</li>
				<li>No data may be shared or sold to third parties without
					consent as per data privacy norms.</li>
			</ul>

			<h2>10. Contract Validity and Termination</h2>
			<ul>
				<li>Initial registration is valid for 2–3 years and renewable
					upon mutual agreement.</li>
				<li>Violation of any terms may result in instant suspension or
					blacklisting by hospital management.</li>
				<li>Either party may terminate the contract with 30 days’
					advance written notice.</li>
			</ul>

			<p>
				<strong>Note:</strong> These conditions are subject to change as per
				updates in legal and policy frameworks. Pharmacies must stay
				compliant with all relevant laws and hospital regulations.
			</p>
		<a href="AddPharmacy.jsf">Back</a>
		</main>	
		
	</div>
	
	<jsp:include page="/footer/Footer.jsp" />
</body>
</html>
