<%@page language="java" contentType="text/html" %>
<%@include file="taglib_includes.jsp" %>
<html>
<%@include file="header_ajax.jsp" %>
<body id="pgHome">
<div id="document">
<%@include file="divheader.jsp" %>
<div id="container">
 <div id="sidebar">
 </div>
 <div id="content">
  <div class="section">
   <span class="errors">${infoMessage}</span>
<script type="text/javascript">
 function submitFormAjax()
 {
    var queryString = $('#searchForm').serialize();
    $.post('main.do', queryString,
     function(data){
         $('#hotelsContainer').html(data);
       });
 }

 function submitCancel(cancelId)
 {
    $('input[name=cancelId]').val(cancelId);
    return $('#cancelForm').submit();
 }
</script>
   <form:form id="searchForm" method="post" action="main.do" commandName="hotelBean">
    <span class="errors">
     <form:errors path="*"/>
    </span>
    <h1>Search Hotels</h1>
    <fieldset>
     <form:input path="searchString" style="width: 165px;"/>&nbsp;
     <input type="button" value="Find Hotels(Ajax)" onclick="submitFormAjax()"/>
     <input type="submit" value="Find Hotels(POST)" />
     <img id="spinner" src="img/spinner.gif" style="display:none"/><br/>
     <span class="label">Maximum results:</span>
     <form:select path="pageSize">
      <form:option label="5" value="5"/>
      <form:option label="10" value="10"/>
      <form:option label="20" value="20"/>
     </form:select>
    </fieldset>
   </form:form>
  </div>
  <div class="section" id="hotelsContainer">
   <%@include file="hotelstable.jsp" %>
  </div>  

  <div class="section">
   <h1>Current Hotel Bookings</h1>
  </div>
  <div class="section">
   <c:if test="${empty bookingBean.bookings}"><span>No Bookings Found</span></c:if>
   <c:if test="${not empty bookingBean.bookings}">
   <form:form id="cancelForm" method="post" action="main.do">
   <table>
    <thead>
     <tr>
      <th>Name</th>
      <th>Address</th>
      <th>City, State</th>
      <th>Check in date</th>
      <th>Check out date</th>
      <th>Conf number</th>
      <th>Action</th>
     </tr>
    </thead>
    <tbody>
     <c:forEach var="booking" items="${bookingBean.bookings}">
     <tr>
      <td>${booking.hotel.name}</td>
      <td>${booking.hotel.address}</td>
      <td><span>${booking.hotel.city}</span>, <span>${booking.hotel.state}</span>, <span>${booking.hotel.country}</span></td>
      <td>${booking.checkinDate}</td>
      <td>${booking.checkoutDate}</td>
      <td>${booking.id}</td>
      <td><a href="#" onclick="submitCancel(<c:out value="${booking.id}"/>);">Cancel</a></td>
     </tr>
     </c:forEach>
    </tbody>
   </table>
   <input type="hidden" name="cancelId"/>
   </form:form>
   </c:if>
  </div>
 </div>
</div>
<div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</body>
</html>