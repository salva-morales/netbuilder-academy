<%--

  GC Page component.

  This is the Gateway Consultant Page

--%><%
%><%@include file="/apps/nb-academy/global.jsp"%><%
%><%@page session="false" %><%
%>
<h1 class="staff-main-title">This page contains the info for the next GC</h1>

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

<cq:include path="main-par" resourceType="foundation/components/parsys" />