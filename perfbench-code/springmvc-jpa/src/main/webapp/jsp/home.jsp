<%@page language="java" contentType="text/html" %>
<%@include file="taglib_includes.jsp" %>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
 <title>JBoss Suites: Spring MVC 2 Framework</title>
 <link href="css/screen.css" rel="stylesheet" type="text/css" />
</head>
<body id="pgHome">
<div id="document">
 <div id="header">
  <div id="title"><img src="img/hdr.title.gif" alt="JBoss Suites: JSF 2 framework demo"/></div>
 </div>
 <div id="container">
  <div id="sidebar">
   <form:form method="post" action="home.do" commandName="loginForm">
    <fieldset>
     <div>
      <span class="label">Login Name</span>
      <form:input path="username" style="width: 175px;"/>
      <div class="errors"><form:errors id="message" path="username"/></div>
     </div>
     <div>
      <span class="label">Password</span>
      <form:password path="password" style="width: 175px;"/>
     </div>
     <div class="errors"><form:errors path="*" /></div>
     <div class="buttonBox"><input type="submit" value="Account Login"/></div>
     <div class="notes"><a href="register.do">Register New User</a></div>
     <div class="subnotes">
      Or use a demo account:
      <ul>
       <li>gavin/foobar</li>
       <li>demo/demo</li>
      </ul>
     </div>
    </fieldset>
   </form:form>
  </div>
 </div>
 <div id="footer">Created with JBoss EJB 3.0, Spring MVC</div>
</div>
</body>
</html>