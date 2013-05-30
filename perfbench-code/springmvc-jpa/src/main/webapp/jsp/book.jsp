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
   <h1>View Hotel</h1>
  </div>
  <div class="section">
<script>
  function validateAjax(command,msgPanel)
  {
    var queryString = $('#form').serialize();
    $.post('book.do?val='+command, queryString,
      function(data){
        $(msgPanel).html(data);
      });
  }
</script>
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
    <div class="label">Nightly rate:</div>
    <div class="output" id="price">${bookingBean.booking.hotel.price}</div>
   </div>
   <form:form id="form" method="post" action="book.do" commandName="bookForm">
     <spring:htmlEscape defaultHtmlEscape="true"/> 
     <div class="entry">
      <div class="label">Check In Date:</div>
      <div class="input" id="checkinDateBorder">
       <fmt:formatDate pattern="yyyy/MM/dd" value="${bookForm.checkinDate}" var="auxCheckinDate"/>
       <input id="checkinDate" name="checkinDate" type="text" value="<c:out value="${auxCheckinDate}"/>"
      </div>
      <div id="checkinDateMsg" class="errors"><%@include file="ajax/book_checkinDate.jsp" %></div>
     </div>		
     <div class="entry">
      <div class="label">Check Out Date:</div>
      <div class="input" id="checkoutDateBorder">
       <fmt:formatDate pattern="yyyy/MM/dd" value="${bookForm.checkoutDate}" var="auxCheckoutDate"/>
       <input id="checkoutDate" name="checkoutDate" type="text" value="<c:out value="${auxCheckoutDate}"/>"
      </div>
      <div id="checkoutDateMsg" class="errors"><%@include file="ajax/book_checkoutDate.jsp" %></div>
     </div>		
     <div class="entry">
      <div class="label">Room Preference:</div>
      <div class="input" id="bedsBorder">
       <form:select path="beds" id="beds">
        <form:option id="OneKingBed" label="One king-size bed" value="1"/>
        <form:option id="TwoDoubleBeds" label="Two double beds" value="2"/>
        <form:option id="ThreeBeds" label="Three beds" value="3"/>
       </form:select>
      </div>
     </div>		
     <div class="entry">
      <div class="label">Smoking Preference:</div>
      <div class="input" wicket:id="smokingBorder">
          <span id="smoking">
              <form:radiobutton path="smoking" value="true" label="Smoking"/>
              <form:radiobutton path="smoking" value="false" label="Non Smoking"/>
          </span>
      </div>
     </div>
     <div class="entry">
      <div class="label">Credit Card #:</div>
      <div class="input" id="creditCardBorder">
       <span>
        <form:input path="creditCard" onblur="validateAjax('creditCard','#creditCardMsg')"/>
        <div id="creditCardMsg" class="errors"><%@include file="ajax/book_creditCard.jsp" %></div>
       </span>
      </div>
     </div>		
     <div class="entry">
      <div class="label">Credit Card Name:</div>
      <div class="input" id="creditCardNameBorder">
       <span>
        <form:input path="creditCardName" onblur="validateAjax('creditCardName','#creditCardNameMsg')"/>
        <div id="creditCardNameMsg" class="errors"><%@include file="ajax/book_creditCardName.jsp" %></div>
       </span>
      </div>
     </div>
     <div class="entry">
      <div class="label">Credit Card Expiry:</div>
      <div class="input" id="creditCardExpiryBorder">
       <form:select path="creditCardExpiryMonth">
        <form:option value="1" label="Jan"/>
        <form:option value="2" label="Feb"/>
        <form:option value="3" label="Mar"/>
        <form:option value="4" label="Apr"/>
        <form:option value="5" label="May"/>
        <form:option value="6" label="Jun"/>
        <form:option value="7" label="Jul"/>
        <form:option value="8" label="Aug"/>
        <form:option value="9" label="Sep"/>
        <form:option value="10" label="Oct"/>
        <form:option value="11" label="Nov"/>
        <form:option value="12" label="Dec"/>
       </form:select>
       <form:select path="creditCardExpiryYear">
        <form:option value="2006" label="2006"/>
        <form:option value="2007" label="2007"/>
        <form:option value="2008" label="2008"/>
        <form:option value="2009" label="2009"/>
        <form:option value="2010" label="2010"/>
       </form:select>
      </div>
     </div>		
     <div class="entry">
      <div class="label"></div>
      <div class="input">
       <input type="submit" name="proceed" value="Proceed"/>
       <input type="submit" name="cancel" value="Cancel"/>
      </div>
     </div>    		
   </form:form>
 </div>
</div>
<div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</div>
</body>
</html>