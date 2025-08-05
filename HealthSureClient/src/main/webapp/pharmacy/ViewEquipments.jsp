<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<f:view>
<html>
<head>
    <title>View Equipment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ViewMedicines.css" />
</head>
<body>
<jsp:include page="/navbar/NavPharmacy.jsp" />

<div class="top">
    <h2>Equipment Stocks</h2>

    <!-- Search -->
<h:form id="searchForm">
    <h:panelGrid columns="5" cellpadding="5" styleClass="search-grid">
        <h:outputLabel for="searchText" value="Search:" />
        <h:inputText id="searchText" value="#{equipmentController.searchText}" />

        <h:selectOneRadio id="searchMode" value="#{equipmentController.searchMode}" styleClass ="radio-inline">
            <f:selectItem itemValue="starts" itemLabel="Starts With" />
            <f:selectItem itemValue="contains" itemLabel="Contains" />
        </h:selectOneRadio>

        <h:commandButton id="searchBtn" value="Search" action="#{equipmentController.searchEquipment}" />
        <h:commandButton id ="resetBtn" value="Reset" action="#{equipmentController.resetSearch}" />
    </h:panelGrid>
</h:form>


    <!-- Table -->
    <h:form id="tableForm">
     <!--   <h:panelGroup rendered="#{not empty equipmentController.equipmentList}">  --> 
            <h:dataTable value="#{equipmentController.paginatedEquipment}" var="med" border="1" styleClass="table">

    <!-- ID -->
<h:column>
        <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="ID" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('equipmentId')}"
                                   rendered="#{!(equipmentController.sortField eq 'equipmentId' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('equipmentId')}"
                                   rendered="#{!(equipmentController.sortField eq 'equipmentId' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
        <h:commandLink value="#{med.equipmentId}" action="#{equipmentController.prepareUpdate(med)}" />
    </h:column>

    <!-- Name (Sortable) -->
    <h:column>
        <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="Name" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('equipmentName')}"
                                   rendered="#{!(equipmentController.sortField eq 'equipmentName' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('equipmentName')}"
                                   rendered="#{!(equipmentController.sortField eq 'equipmentName' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
        <h:commandLink value="#{med.equipmentName}" action="#{equipmentController.prepareUpdate(med)}" styleClass ="desc-column" />
    </h:column>

    <!-- Description -->
    <h:column>
        <f:facet name="header">
        <h:panelGroup layout="block" style="display: flex; align-items: center;">
        <h:outputText value="Description" />
        <h:panelGroup style="margin-left: 5px;">
        <h:commandLink action="#{equipmentController.sortByAsc('description')}"
                                   rendered="#{!(equipmentController.sortField eq 'description' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('description')}"
                                   rendered="#{!(equipmentController.sortField eq 'description' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        
        </f:facet>
        <h:commandLink value="#{med.description}" action="#{equipmentController.prepareUpdate(med)}" styleClass ="desc-column" />
    </h:column>

    <!-- Quantity (Sortable) -->
    <h:column>
        <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="Stock" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('quantityInStock')}"
                                   rendered="#{!(equipmentController.sortField eq 'quantityInStock' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('quantityInStock')}"
                                   rendered="#{!(equipmentController.sortField eq 'quantityInStock' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
        <h:commandLink value="#{med.quantity}" action="#{equipmentController.prepareUpdate(med)}" styleClass ="desc-column"/>
    </h:column>

    <!-- Unit Price (Sortable) -->
    <h:column>
         <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="Price" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('unitPrice')}"
                                   rendered="#{!(equipmentController.sortField eq 'unitPrice' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('unitPrice')}"
                                   rendered="#{!(equipmentController.sortField eq 'unitPrice' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
        <h:commandLink value="#{med.unitPrice}" action = "#{equipmentController.prepareUpdate(med) }" />
    </h:column>

    <!-- Purchase Date  -->
    <h:column>
        <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="Purchase Date" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('purchaseDate')}"
                                   rendered="#{!(equipmentController.sortField eq 'purchaseDate' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('purchaseDate')}"
                                   rendered="#{!(equipmentController.sortField eq 'purchaseDate' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
        <h:commandLink value="#{med.purchaseDate}" action = "#{equipmentController.prepareUpdate(med) }" />       
    </h:column>
    
     <!-- Status  -->
    <h:column>
    <f:facet name="header">
            <h:panelGroup layout="block" style="display: flex; align-items: center;">
                <h:outputText value="Status" />
                <h:panelGroup style="margin-left: 5px;">
                    <h:commandLink action="#{equipmentController.sortByAsc('status')}"
                                   rendered="#{!(equipmentController.sortField eq 'status' and equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▲</h:commandLink>
                    <h:commandLink action="#{equipmentController.sortByDesc('status')}"
                                   rendered="#{!(equipmentController.sortField eq 'status' and not equipmentController.sortAscending)}"
                                   style="margin: 0 3px;">▼</h:commandLink>
                </h:panelGroup>
            </h:panelGroup>
        </f:facet>
    <h:commandLink value = "#{med.status }" action = "#{equipmentController.prepareUpdate(med)}" />  
    </h:column>
  
  
</h:dataTable>

            <!-- Pagination Controls -->
            	<div
					style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">

					<!-- Showing X–Y out of Z -->
					<h:outputText
						value="#{equipmentController.showingFrom}-#{equipmentController.showingTo} out of #{equipmentController.totalRecords}"
						style="margin-bottom: 10px;" />

					<!-- Pagination Controls -->
					<div>
						<!-- Prev Button -->
						<h:commandButton value="Prev ←"
							action="#{equipmentController.previousPage}"
							disabled="#{equipmentController.currentPage == 1}"
							styleClass="page-button" />

						<!-- Page Info -->
						<h:outputText
							value=" Page #{equipmentController.currentPage} of #{equipmentController.totalPages} "
							style="margin: 0 10px;" />

						<!-- Next Button -->
						<h:commandButton value="Next →"
							action="#{equipmentController.nextPage}"
							disabled="#{equipmentController.currentPage == equipmentController.totalPages}"
							styleClass="page-button" />
					</div>


				</div>
     <!--    </h:panelGroup>  -->

        <!-- No Results Message -->
        <h:panelGroup rendered="#{equipmentController.searchPerformed and empty equipmentController.equipmentList}">
            <div style="text-align:center; color:red; margin-top:20px;">
                <h:outputText value = "No result found for: " />
                <h:outputText value = "#{equipmentController.searchText}" style = "font-weight:bold;" />
            </div>
        </h:panelGroup>
    </h:form>
</div>

<jsp:include page="/footer/Footer.jsp"/>
</body>
</html>
</f:view>
