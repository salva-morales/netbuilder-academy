<%--

  GC finder component.

  Use this form component to find GC sorted by skills

--%><%
%><%@include file="/apps/nb-academy/global.jsp"%><%
%><%@page session="false" %><%
%>

<nb-academy:listAvailableGC />
This is the list of all available GCs:<br/>
<c:forEach var="gc" items="${availableGCs}" varStatus="i">
	<h3>${gc.name}</h3>
</c:forEach>

<div class="container">

        Insert the skill to filter:
        <input type="text" id="skill-name" name="skill-form" placeholder="Insert skill" required>

    <br/>
    <div class="list-gc__grid-filters">
        <span class="">This is the list of all available GCs with the following skill: ${param.skill}</span>
        <ul id="list-gc__results">
        </ul>
    </div>
</div>
