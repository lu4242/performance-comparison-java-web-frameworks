<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>JBoss Suites: Grails</title>
  <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}"/>
  <g:layoutHead/>
</head>
<body>
<div id="document">
  <div id="header">
    <div id="title"><img src="${resource(dir:'img',file:'hdr.title.gif')}" alt="JBoss Suites: Grails demo"/></div>
    <div id="status">
      Welcome ${session.user.name}
      | <g:link controller="main" action="index">Search</g:link>
      | <g:link controller="user" action="password">Settings</g:link>
      | <g:link controller="user" action="logout">Logout</g:link>
    </div>
  </div>
  <div id="container">
    <div id="sidebar"></div>
    <div id="content">
      <g:layoutBody/>
    </div>
  </div>
  <div id="footer">Created with Grails</div>
</div>
</body>
</html>