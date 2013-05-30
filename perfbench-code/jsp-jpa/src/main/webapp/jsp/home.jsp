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
   <form method="post" action="home.do">
    <fieldset>
     <div>
      <span class="label">Login Name</span>
      <input type="text" name="username" style="width: 175px;" value="${loginForm.username}"/>
      <div class="errors"><c:out value="${messages.messageMap['username']}"/></div>
     </div>
     <div>
      <span class="label">Password</span>
      <input type="password" name="password" style="width: 175px;" value="${loginForm.password}"/>
     </div>
     <div class="errors"><%@include file="msg/messages.jsp" %></div>
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
   </form>
  </div>
 </div>
 <div id="footer">Created with JBoss EJB 3.0, Servlet and JSP</div>
</div>
</body>
</html>