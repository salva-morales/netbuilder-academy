<%--

  GC Page component.

  This is the Gateway Consultant Page

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%>
<h1 class="staff-main-title">This page contains the info for the next GC</h1>

<cq:include path="gc" resourceType="nb-academy/components/gateway-consultant" />

<cq:include path="main-par" resourceType="foundation/components/parsys" />