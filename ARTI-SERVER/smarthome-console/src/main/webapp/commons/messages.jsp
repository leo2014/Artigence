<%@ include file="/commons/taglibs.jsp" %>

<%-- Error Messages --%>
<c:if test="${not empty springMessages}">
	<div class="message">
        <c:forEach var="msg" items="${springMessages}">
			<img src="" alt="Info"/>${msg}<br/>
        </c:forEach>
	</div>    
</c:if>

<%-- Info Messages --%>
<c:if test="${not empty springErrors}">
	<div class="error">
        <c:forEach var="errorMsg" items="${springErrors}">
			<img src="" alt="Warning"/>${errorMsg}<br/>
        </c:forEach>
	</div>    
</c:if>



