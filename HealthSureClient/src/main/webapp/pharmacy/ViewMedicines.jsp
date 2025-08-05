<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<f:view>
	<html>
<head>
<title>View Medicines</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/ViewMedicines.css" />
</head>
<body>
	<jsp:include page="/navbar/NavPharmacy.jsp" />

	<div class="top">
		<h2>Medicine Stocks</h2>

		<!-- Search -->
		<h:form id="searchForm">
			<h:panelGrid columns="5" cellpadding="5" styleClass="search-grid">
				<h:outputLabel value="Search by name:" />
				<h:inputText value="#{medicinesController.searchText}" />
				<h:selectOneRadio value="#{medicinesController.searchMode}"
					layout="lineDirection" styleClass="radio-inline">
					<f:selectItem itemValue="starts" itemLabel="Starts With" />
					<f:selectItem itemValue="contains" itemLabel="Contains" />
				</h:selectOneRadio>
				<h:commandButton id="searchBtn" value="Search"
					action="#{medicinesController.searchMedicines}" />
				<h:commandButton id="resetBtn" value="Reset"
					action="#{medicinesController.resetSearch}" />

			</h:panelGrid>
		</h:form>


		<!-- Table -->
		<h:form id="tableForm">
			<h:panelGroup
				rendered="#{not empty medicinesController.medicinesList}">
				<h:dataTable value="#{medicinesController.paginatedMedicines}"
					var="med" border="1" styleClass="table">

					<!-- Medicine ID -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="ID" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('medicineId')}"
										rendered="#{!(medicinesController.sortField eq 'medicineId' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('medicineId')}"
										rendered="#{!(medicinesController.sortField eq 'medicineId' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:commandLink value="#{med.medicineId}"
							action="#{medicinesController.prepareUpdate(med)}" />
					</h:column>

					<!-- Medicine Name -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="Name" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('medicineName')}"
										rendered="#{!(medicinesController.sortField eq 'medicineName' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('medicineName')}"
										rendered="#{!(medicinesController.sortField eq 'medicineName' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:commandLink value="#{med.medicineName}"
							action="#{medicinesController.prepareUpdate(med)}" />
					</h:column>

					<!-- Description -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="Description" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('description')}"
										rendered="#{!(medicinesController.sortField eq 'description' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('description')}"
										rendered="#{!(medicinesController.sortField eq 'description' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>

						</f:facet>
						<h:commandLink value="#{med.description}"
							action="#{medicinesController.prepareUpdate(med)}" />
					</h:column>

					<!-- Stock -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="Stock" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('quantityInStock')}"
										rendered="#{!(medicinesController.sortField eq 'quantityInStock' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('quantityInStock')}"
										rendered="#{!(medicinesController.sortField eq 'quantityInStock' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:commandLink value="#{med.quantityInStock}"
							action="#{medicinesController.prepareUpdate(med)}" />
					</h:column>

					<!-- Price -->
					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="Price" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('unitPrice')}"
										rendered="#{!(medicinesController.sortField eq 'unitPrice' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('unitPrice')}"
										rendered="#{!(medicinesController.sortField eq 'unitPrice' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:commandLink value="#{med.unitPrice}"
							action="#{medicinesController.prepareUpdate(med)}" />
					</h:column>

					<!-- Expiry Date -->

					<h:column>
						<f:facet name="header">
							<h:panelGroup layout="block"
								style="display: flex; align-items: center;">
								<h:outputText value="Expiry Date" />
								<h:panelGroup style="margin-left: 5px;">
									<h:commandLink
										action="#{medicinesController.sortByAsc('expiryDate')}"
										rendered="#{!(medicinesController.sortField eq 'expiryDate' and medicinesController.sortAscending)}"
										style="margin: 0 3px;">▲</h:commandLink>
									<h:commandLink
										action="#{medicinesController.sortByDesc('expiryDate')}"
										rendered="#{!(medicinesController.sortField eq 'expiryDate' and not medicinesController.sortAscending)}"
										style="margin: 0 3px;">▼</h:commandLink>
								</h:panelGroup>
							</h:panelGroup>
						</f:facet>
						<h:commandLink action="#{medicinesController.prepareUpdate(med)}">
							<h:outputText value="#{med.expiryDate}">
								<f:attribute name="style"
									value="#{med.expiryDate.before(medicinesController.currentDate) ? 'color:red; font-weight:bold;' : ''}" />
							</h:outputText>
						</h:commandLink>
					</h:column>

				</h:dataTable>

				<!-- Pagination Controls -->
				<!-- Pagination and Record Info Container -->
				<div
					style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">

					<!-- Showing X–Y out of Z -->
					<h:outputText
						value="#{medicinesController.showingFrom}-#{medicinesController.showingTo} out of #{medicinesController.totalRecords}"
						style="margin-bottom: 10px;" />

					<!-- Pagination Controls -->
					<div>
						<!-- Prev Button -->
						<h:commandButton value="Prev ←"
							action="#{medicinesController.previousPage}"
							disabled="#{medicinesController.currentPage == 1}"
							styleClass="page-button" />

						<!-- Page Info -->
						<h:outputText
							value=" Page #{medicinesController.currentPage} of #{medicinesController.totalPages} "
							style="margin: 0 10px;" />

						<!-- Next Button -->
						<h:commandButton value="Next →"
							action="#{medicinesController.nextPage}"
							disabled="#{medicinesController.currentPage == medicinesController.totalPages}"
							styleClass="page-button" />
					</div>


				</div>

			</h:panelGroup>

			<!-- No Results Message -->
			<h:panelGroup
				rendered="#{ medicinesController.searchPerformed and empty medicinesController.medicinesList }">
				<div style="text-align: center; color: red; margin-top: 20px;">
					<h:outputText value="No result found for: " />
					<h:outputText value="#{medicinesController.searchText}"
						style="font-weight:bold;" />
				</div>
			</h:panelGroup>

		</h:form>
	</div>

	<jsp:include page="/footer/Footer.jsp" />
</body>
	</html>
</f:view>
