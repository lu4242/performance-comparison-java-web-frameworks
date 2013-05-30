<%@page language="java" contentType="text/html" %>
<%@include file="taglib_includes.jsp" %>
<c:if test="${empty hotelBean.hotels}"><span wicket:id="noResultsContainer">No Hotels Found</span></c:if>
<c:if test="${not empty hotelBean.hotels}">
<table>
 <thead>
  <tr>
   <th>Name</th>
   <th>Address</th>
   <th>City, State</th>
   <th>Zip</th>
   <th>Action</th>
  </tr>
 </thead>
 <tbody>
  <c:forEach var="hotel" items="${hotelBean.hotels}">
  <tr>
   <td>${hotel.name}</td>
   <td>${hotel.address}</td>
   <td><span>${hotel.city}</span>, <span>${hotel.state}</span>, <span>${hotel.country}</span></td>
   <td>${hotel.zip}</td>
   <td><a href="hotel.do?id=${hotel.id}">View Hotel</a></td>
  </tr>
  </c:forEach>
 </tbody>
</table>
</c:if>
<c:if test="${hotelBean.nextPageAvailable}">
<a href="main.do?action=nextPage" id="moreResultsLink">More results</a>
</c:if>