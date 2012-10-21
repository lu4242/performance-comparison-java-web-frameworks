<html>
<head>
  <meta name="layout" content="template"/>
</head>
<body>
  <div class="section">
    <h1>Change Your Password</h1>
  </div>
  <g:if test="${flash.message}">
    <div class="errors">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${user}">
    <div class="errors">
        <g:renderErrors bean="${user}" as="list" />
    </div>
  </g:hasErrors>
  <div class="section">
    <g:form action="savePassword">
      <fieldset>
        <div class="entry">
          <div class="label">Password:</div>
          <div class="input">
            <input type="password" name="password" value="${password}"/>
          </div>
        </div>
        <div class="entry">
          <div class="label">Verify:</div>
          <div class="input">
            <input type="password" name="verify" value="${verify}"/>
          </div>
        </div>
        <div class="entry">
          <div class="label"></div>
          <div class="input">
            <input type="submit" value="Change"/>
            <g:actionSubmit value="Cancel" action="cancel"/>
          </div>
        </div>
      </fieldset>
    </g:form>
  </div>
</body>
</html>

