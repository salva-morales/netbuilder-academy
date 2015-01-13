<%--
    
    GC finder component.
    
    Use this form component to find GC sorted by skills
    
    --%><%
    %><%@include file="/apps/nb-academy/global.jsp"%><%
    %><%@page session="false" %><%
    %>

<div class="container">
    
    Insert the skill to filter:
    <input type="text" id="skill-name" name="skills" placeholder="Insert skill" required>
    
    <br/>
    <div class="list-gc__grid-filters">
        <span>This is the list of all available GCs with the following skill: <span id="skill_to_sort"></span></span>
        <ul id="list-gc__results">
        </ul>
    </div>
</div>
