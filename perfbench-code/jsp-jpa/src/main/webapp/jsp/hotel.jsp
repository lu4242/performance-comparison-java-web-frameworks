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
     <div class="output" id="name">${hotel.name}</div>
    </div>
    <div class="entry">
     <div class="label">Address:</div>
     <div class="output" id="address">${hotel.address}</div>
    </div>
    <div class="entry">
     <div class="label">City:</div>
     <div class="output" id="city">${hotel.city}</div>
    </div>
    <div class="entry">
     <div class="label">State:</div>
     <div class="output" id="state">${hotel.state}</div>
    </div>
    <div class="entry">
     <div class="label">Zip:</div>
     <div class="output" id="zip">${hotel.zip}</div>
    </div>
    <div class="entry">
     <div class="label">Country:</div>
     <div class="output" id="country">${hotel.country}</div>
    </div>
    <div class="entry">
     <div class="label">Nightly rate:</div>
     <div class="output" id="price">${hotel.price}</div>
    </div>
   </div>
   <div class="section">
    <form id="form" method="post" action="hotel.do">
     <fieldset class="buttonBox">
      <input type="submit" name="book" value="Book Hotel"/>
      <input type="submit" name="cancel" value="Back to Search"/>
     </fieldset>
    </form>
   </div>        
  </div>
  <div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
 </div>
</div>
</body>
</html>