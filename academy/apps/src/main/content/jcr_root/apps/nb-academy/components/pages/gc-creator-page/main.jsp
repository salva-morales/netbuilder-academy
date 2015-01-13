<%--

  GC Page component.

  This is the Gateway Consultant Page

--%><%
    %><%@include file="/apps/nb-academy/global.jsp"%><%
	%><%@ page import="com.day.cq.wcm.api.WCMMode"%><%
    %><%@page session="false" %><%
%><%WCMMode disabled = WCMMode.DISABLED.toRequest(request);%>
<h1 class="staff-main-title">Use this tool to create a new GC</h1>

<cq:include path="main-par" resourceType="foundation/components/parsys" />

<%disabled.toRequest(request);%>