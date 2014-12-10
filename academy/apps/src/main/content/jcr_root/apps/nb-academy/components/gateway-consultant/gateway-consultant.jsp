<%--
    
    Gateway Consultant component.
    
    Use this component to insert the information related to a Gateway Consultant
    
    --%><%
    %><%@include file="/libs/foundation/global.jsp"%><%
    %><%@page session="false" %><%
    %>
<cq:includeClientLib categories="apps.nb-academy.components.all" />
<c:set var="availability" value="${properties.availability ? 'Available' : 'No Available'}" />


<div class="gc-info">

    <div class="gc-name">
        Name: ${properties.name}
    </div>
	<div class="gc-age">
        Age: ${properties.age} <br>
    </div>
	<div class="gc-availability">
        Availability: ${availability} <br>
    </div>

</div>