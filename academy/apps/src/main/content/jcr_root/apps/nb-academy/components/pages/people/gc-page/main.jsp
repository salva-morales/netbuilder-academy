<%--
    
    GC Page component.
    
    This is the Gateway Consultant Page
    
    --%><%
    %><%@include file="/apps/nb-academy/global.jsp"%><%
    %><%@page session="false" %><%
    %>

<c:set var="availability" value="${properties.availability ? 'Available' : 'No available'}"/>

<h1 class="staff-main-title">This page contains the info for the next GC</h1>

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
    <div class="gc-nationality">
        Nationality: ${properties.nationality}
    </div>

    Skills:
    <ul class="gc-skills">
        <c:forEach begin="0" end="${fn:length(properties.skills) - 1}" var="index">
            <li>${properties.skills[index]}</li>
		 </c:forEach>
    </ul>

    <div class="gc-description">
        Description: ${properties.description} <br>
    </div>

</div>

<cq:include path="main-par" resourceType="foundation/components/parsys" />