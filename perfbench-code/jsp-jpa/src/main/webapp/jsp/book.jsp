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
   <form id="form" method="post" action="book.do">
    <div class="entry">
     <div class="label">Check In Date:</div>
     <div class="input" id="checkinDateBorder">
      <fmt:formatDate pattern="yyyy/MM/dd" value="${bookForm.checkinDate}" var="auxCheckinDate"/>
      <input id="checkinDate" name="checkinDate" type="text" value="<c:out value="${auxCheckinDate}"/>"/>
     </div>
     <div id="checkinDateMsg" class="errors"><%@include file="ajax/book_checkinDate.jsp" %></div>
    </div>		
    <div class="entry">
     <div class="label">Check Out Date:</div>
     <div class="input" id="checkoutDateBorder">
      <fmt:formatDate pattern="yyyy/MM/dd" value="${bookForm.checkoutDate}" var="auxCheckoutDate"/>
      <input id="checkoutDate" name="checkoutDate" type="text" value="<c:out value="${auxCheckoutDate}"/>"/>
     </div>
     <div id="checkoutDateMsg" class="errors"><%@include file="ajax/book_checkoutDate.jsp" %></div>
    </div>		
    <div class="entry">
     <div class="label">Room Preference:</div>
     <div class="input" id="bedsBorder">
      <select name="beds" id="beds">
       <option id="OneKingBed" value="1" <c:if test="${bookForm.beds == 1}">selected="true"</c:if>>One king-size bed</option>
       <option id="TwoDoubleBeds" value="2" <c:if test="${bookForm.beds == 2}">selected="true"</c:if>>Two double beds</option>
       <option id="ThreeBeds" value="3" <c:if test="${bookForm.beds == 3}">selected="true"</c:if>>Three beds</option>
      </select>
     </div>
    </div>		
    <div class="entry">
     <div class="label">Smoking Preference:</div>
     <div class="input" id="smokingBorder">
      <span id="smoking">
       <input type="radio" name="smoking" value="true" <c:if test="${bookForm.smoking == true}">checked="true"</c:if>/>Smoking
       <input type="radio" name="smoking" value="false" <c:if test="${bookForm.smoking == false}">checked="true"</c:if>/>Non Smoking
      </span>
     </div>
    </div>
    <div class="entry">
     <div class="label">Credit Card #:</div>
     <div class="input" id="creditCardBorder">
      <span>
       <input type="text" name="creditCard" onblur="validateAjax('creditCard','#creditCardMsg')" value="${bookForm.creditCard}"/>
       <div id="creditCardMsg" class="errors"><%@include file="ajax/book_creditCard.jsp" %></div>
      </span>
     </div>
    </div>		
    <div class="entry">
     <div class="label">Credit Card Name:</div>
     <div class="input" id="creditCardNameBorder">
      <span>
       <input type="text" name="creditCardName" onblur="validateAjax('creditCardName','#creditCardNameMsg')" value="${bookForm.creditCardName}"/>
       <div id="creditCardNameMsg" class="errors"><%@include file="ajax/book_creditCardName.jsp" %></div>
      </span>
     </div>
    </div>
    <div class="entry">
     <div class="label">Credit Card Expiry:</div>
     <div class="input" id="creditCardExpiryBorder">
      <select name="creditCardExpiryMonth">
       <option value="1" <c:if test="${bookForm.creditCardExpiryMonth == 1}">selected="true"</c:if>>Jan</option>
       <option value="2" <c:if test="${bookForm.creditCardExpiryMonth == 2}">selected="true"</c:if>>Feb</option>
       <option value="3" <c:if test="${bookForm.creditCardExpiryMonth == 3}">selected="true"</c:if>>Mar</option>
       <option value="4" <c:if test="${bookForm.creditCardExpiryMonth == 4}">selected="true"</c:if>>Apr</option>
       <option value="5" <c:if test="${bookForm.creditCardExpiryMonth == 5}">selected="true"</c:if>>May</option>
       <option value="6" <c:if test="${bookForm.creditCardExpiryMonth == 6}">selected="true"</c:if>>Jun</option>
       <option value="7" <c:if test="${bookForm.creditCardExpiryMonth == 7}">selected="true"</c:if>>Jul</option>
       <option value="8" <c:if test="${bookForm.creditCardExpiryMonth == 8}">selected="true"</c:if>>Aug</option>
       <option value="9" <c:if test="${bookForm.creditCardExpiryMonth == 9}">selected="true"</c:if>>Sep</option>
       <option value="10" <c:if test="${bookForm.creditCardExpiryMonth == 10}">selected="true"</c:if>>Oct</option>
       <option value="11" <c:if test="${bookForm.creditCardExpiryMonth == 11}">selected="true"</c:if>>Nov</option>
       <option value="12" <c:if test="${bookForm.creditCardExpiryMonth == 12}">selected="true"</c:if>>Dec</option>
      </select>
      <select name="creditCardExpiryYear">
       <option value="2006" <c:if test="${bookForm.creditCardExpiryYear == 2006}">selected="true"</c:if>>2006</option>
       <option value="2007" <c:if test="${bookForm.creditCardExpiryYear == 2007}">selected="true"</c:if>>2007</option>
       <option value="2008" <c:if test="${bookForm.creditCardExpiryYear == 2008}">selected="true"</c:if>>2008</option>
       <option value="2009" <c:if test="${bookForm.creditCardExpiryYear == 2009}">selected="true"</c:if>>2009</option>
       <option value="2010" <c:if test="${bookForm.creditCardExpiryYear == 2010}">selected="true"</c:if>>2010</option>
      </select>
     </div>
    </div>		
    <div class="entry">
     <div class="label"></div>
     <div class="input">
      <input type="submit" name="proceed" value="Proceed"/>
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