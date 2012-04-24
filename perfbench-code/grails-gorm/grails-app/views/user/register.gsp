<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>JBoss Suites: Grails</title>
  <link rel="stylesheet" href="${resource(dir:'css',file:'screen.css')}"/>
  <g:javascript library="prototype"/>
</head>
<body id="pgHome">
  <div id="document">
    <div id="header">
      <div id="title"><img src="${resource(dir:'img',file:'hdr.title.gif')}" alt="JBoss Suites: Grails demo"/></div>
    </div>
    <div id="container">
      <div id="sidebar"></div>
      <div id="content">
        <div class="section">
          <h1>Register</h1>
        </div>
        <div class="section">
          <g:form action="save" method="post">
            <fieldset>
              <div class="entry">
                <div class="label">Username:</div>
                <div class="input">
                  <g:formval bean="${user}" name="username"/>
                </div>
              </div>
              <div class="entry">
                <div class="label">Real Name:</div>
                <div class="input">
                  <g:formval bean="${user}" name="name"/>
                </div>
              </div>
              <div class="entry">
                <div class="label">Password:</div>
                <div class="input">
                  <g:formval type="password" bean="${user}" name="password"/>
                </div>
              </div>
              <div class="entry">
                <div class="label">Verify Password:</div>
                <div class="input">
                   <input type="password" name="verify" value="${verify}"/>
                </div>
              </div>
              <div class="entry">
                <div class="label"></div>
                <div class="input">
                  <input type="submit" value="Register"/>
                  <g:actionSubmit value="Cancel" action="cancel"/>
                </div>
              </div>
            </fieldset>
          </g:form>
        </div>
      </div>
    </div>
    <div id="footer">Created with Grails</div>
  </div>
</body>
</html>

