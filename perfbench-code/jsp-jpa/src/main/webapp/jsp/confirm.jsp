<%@page language="java" contentType="text/html" %>
<%@include file="taglib_includes.jsp" %>
<html>
<%@include file="header.jsp" %>
<body id="pgHome">
<div id="document">
<%@include file="divheader.jsp" %>
<div id="container">
 <div id="sidebar">
 </div>
 <div id="content">
  <div class="section">
   <h1>View Hotel</h1>
  </div>
  <div class="section">
   <div class="entry">
    <div class="label">Name:</div>
    <div class="output" id="name">${bookingBean.booking.hotel.name}</div>
   </div>
   <div class="entry">
    <div class="label">Address:</div>
    <div class="output" id="address">${bookingBean.booking.hotel.address}</div>
   </div>
   <div class="entry">
    <div class="label">City, State:</div>
    <div class="output">
     <span id="hotel_city">${bookingBean.booking.hotel.city}</span>,
     <span id="hotel_state">${bookingBean.booking.hotel.state}</span>
    </div>
   </div>
   <div class="entry">
    <div class="label">Zip:</div>
    <div class="output" id="zip">${bookingBean.booking.hotel.zip}</div>
   </div>
   <div class="entry">
    <div class="label">Country:</div>
    <div class="output" id="country">${bookingBean.booking.hotel.country}</div>
   </div>
   <div class="entry">
    <div class="label">Total payment:</div>
    <fmt:formatNumber currencySymbol="$" value="${bookingBean.booking.total}" var="auxTotal"/>
    <div class="output" id="total"><c:out value="${auxTotal}"/></div>
   </div>
   <div class="entry">
    <div class="label">Check In Date:</div>
    <fmt:formatDate pattern="yyyy/MM/dd" value="${bookingBean.booking.checkinDate}" var="auxCheckinDate"/>
    <div class="output" id="checkinDate"><c:out value="${auxCheckinDate}"/></div>
   </div>
   <div class="entry">
    <fmt:formatDate pattern="yyyy/MM/dd" value="${bookingBean.booking.checkoutDate}" var="auxCheckoutDate"/>
    <div class="label">Check Out Date:</div>
    <div class="output" id="checkoutDate"><c:out value="${auxCheckoutDate}"/></div>
   </div>
   <div class="entry">
    <div class="label">Credit Card #:</div>
    <div class="output" wicket:id="creditCard">${bookingBean.booking.creditCard}</div>
   </div>
   <form id="form" method="post" action="confirm.do">    
    <div class="entry">
     <div class="label"></div>
     <div class="input">
      <input type="submit" name="confirm" value="Confirm"/>
      <input type="submit" name="revise" value="Revise"/>
      <input type="submit" name="cancel" value="Cancel"/>
     </div>
    </div>    
   </form>
 </div>
</div>
<div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</div>
</body>
</html>