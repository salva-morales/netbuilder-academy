<%--
    
    List all GCs component.
    
    Use this form component to find GC sorted by skills
    
    --%><%
    %><%@include file="/apps/nb-academy/global.jsp"%><%
    %><%@page session="false" %><%
    %>

<nb-academy:listAvailableGC />


<div class="container">

    This is the list of all available GCs:<br/>
    <c:forEach var="gc" items="${availableGCs}" varStatus="i">
        <h3>${gc.name}</h3>
    </c:forEach>

</div>
