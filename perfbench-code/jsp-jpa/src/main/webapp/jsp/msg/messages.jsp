<%@page language="java" contentType="text/html" %>
<%@include file="../taglib_includes.jsp" %>
<c:if test="${messages.messageCount > 0}"><ul>
<c:forEach var="msg" items="${messages.messageList}">
<li><c:out value="${msg.value}"/></li>
</c:forEach>
</ul></c:if>