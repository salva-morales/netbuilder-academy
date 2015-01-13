<%--
    
    GC Creator component.
    
    Use this component to create GCs
    
    --%><%
    %><%@include file="/apps/nb-academy/global.jsp"%><%
    %><%@page session="false" %>

<form id="gc-creator-form" method="post" action="${resource.path}.generate">
    
    <label for="gc-name">Name: </label>
    <input type="text" id="gc-name" name="name" placeholder="Salvador Morales" required>
    <br/><br/>
    
    <label for="gc-age">Age: </label>
    <select id="gc-age" name="age">
        <c:forEach var="age" begin="18" end="65">
            <option value="${age}">${age}</option>
        </c:forEach>
    </select>
    <br/><br/>
    
    <label for="gc-availability">Availability: </label>
    <input type="radio" class="gc-availability" name="availability" value="true" checked>Yes
    <input type="radio" class="gc-availability" name="availability" value="false">No
    <br/><br/>

    <label for="gc-nationality">Nationality: </label>
    <input type="text" id="gc-nationality" name="nationality" placeholder="Spanish" required>
    <br/><br/>

    <label for="gc-skill">Skills: </label>
    <ul id="skill_list">
    	<li>
            <input type="text" class="skill-item" name="skills" placeholder="Java, AEM, CQ, JavaScript, HTML, CSS, C++" required>
        </li>
    </ul>
    <input type="button" id="add_skill" value="Add a new skill">
    <input type="button" id="remove_skill" value="Remove the last skill">
    <br/><br/>

    <label for="gc-description">Description: </label>
    <textarea rows="4" cols="50" id="gc-description" name="description" placeholder="Enter some text here..."></textarea>
    <br/><br/>
    
    <div class="action-buttons">
        <input type="reset" id="reset-button" value="Reset information"/>
        <input type="submit" id="save-button" value="Save GC"/>     
    </div>
    
    
</form>
