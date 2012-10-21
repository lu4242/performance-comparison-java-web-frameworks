<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>JBoss Suites: Grails</title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}"/>
</head>
<body id="pgHome">
  <div id="document">
    <div id="header">
      <div id="title"><img src="${resource(dir:'img',file:'hdr.title.gif')}" alt="JBoss Suites: Grails demo"/></div>
    </div>
    <div id="container">
      <div id="sidebar">
        <g:form controller="user" action="login">
          <fieldset>
            <div>
              <span class="label">Login Name</span>
              <input name="username" value="${username}" style="width: 175px;"/>
            </div>
            <div>
              <span class="label">Password</span>
              <input type="password" name="password" style="width: 175px;"/>
            </div>
            <g:if test="${flash.message}">
              <div class="errors">${flash.message}</div>
            </g:if>
            <div class="buttonBox"><input type="submit" value="Account Login"/></div>
            <div class="notes"><g:link controller="user" action="register">Register New User</g:link></div>
            <div class="subnotes">
              Or use a demo account:
              <ul>
                <li>gavin/foobar</li>
                <li>demo/demo</li>
              </ul>
            </div>
          </fieldset>
        </g:form>
      </div>
    </div>
    <div id="footer">Created with Grails</div>
  </div>
</body>
</html>
