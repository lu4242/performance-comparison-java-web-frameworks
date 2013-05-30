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
    <span class="errors">
        ${infoMessage}
    </span>
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
   <form id="searchForm" method="post" action="main.do">
    <span class="errors"><%@include file="msg/messages.jsp" %></span>
    <h1>Search Hotels</h1>
    <fieldset>
     <input type="text" name="searchString" style="width: 165px;" value="${hotelBean.searchString}"/>&nbsp;
     <input type="button" value="Find Hotels(Ajax)" onclick="submitFormAjax()"/>
     <input type="submit" value="Find Hotels(POST)" />
     <img id="spinner" src="img/spinner.gif" style="display:none"/><br/>
     <span class="label">Maximum results:</span>
     <select name="pageSize">
      <option value="5" <c:if test="${hotelBean.pageSize == 5}">selected="true"</c:if>>5</option>
      <option value="10" <c:if test="${hotelBean.pageSize == 10}">selected="true"</c:if>>10</option>
      <option value="20" <c:if test="${hotelBean.pageSize == 20}">selected="true"</c:if>>20</option>
     </select>
    </fieldset>
   </form>
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
    <form id="cancelForm" method="post" action="main.do">
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
     </form>
    </c:if>
   </div>
  </div>
 </div>
 <div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</body>
</html>
