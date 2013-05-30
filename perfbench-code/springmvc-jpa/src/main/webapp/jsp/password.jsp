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
  <h1>Change Your Password</h1>
 </div>
 <div class="section">
  <form:form id="form" method="post" action="password.do" commandName="changePasswordForm">
   <fieldset>
    <div class="entry">
     <div class="label">Password:</div>
     <div class="input" wicket:id="passwordBorder">
      <form:password path="password" />
     </div>
    </div>
    <div class="entry">
     <div class="label">Verify:</div>
     <div class="input" wicket:id="verifyBorder">
      <form:password path="verify" />
     </div>
    </div>
    <div class="entry">
     <div class="label"></div>
     <div class="input">
      <input type="submit" name="change" value="Change"/>
      <input type="submit" name="cancel" value="Cancel"/>
     </div>
    </div>        
   </fieldset>		
  </form:form>
 </div>
</div>
<div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</div>
</body>
</html>