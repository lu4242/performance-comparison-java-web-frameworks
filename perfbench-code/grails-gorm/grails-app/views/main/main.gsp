<html>
<head>     
  <meta name="layout" content="template"/>
  <g:javascript library="prototype"/>
  <script>
    var doSearch = function() {
      new Ajax.Updater('hotels', '${createLink(action: 'search')}', {
        parameters: $('searchForm').serialize(),
        onCreate: function() {Element.show('spinner')},
        onComplete: function() {Element.hide('spinner')}
      });
      return false;
    };
  </script>
</head>
<body>
  <div class="section">
      <g:form name="searchForm" onsubmit="doSearch()">
          <g:if test="${flash.message}">
            <div class="errors">${flash.message}</div>
          </g:if>
          <g:if test="${message}">
            <div class="errors">${message}</div>
          </g:if>
          <h1>Search Hotels</h1>
          <fieldset>
              <input name="searchString" value="${session.searchString}" style="width: 165px;" onkeyup="doSearch()"/>&nbsp;
              <input type="submit" value="Find Hotels"/>
              <img id="spinner"src="${resource(dir:'img',file:'spinner.gif')}" style="display:none"/><br/>
              <span class="label">Maximum results:</span>
              <g:select name="pageSize" value="${session.pageSize}" from="${[5, 10, 20]}"/>
          </fieldset>
      </g:form>
  </div>
  
  <g:render template="hotels"/>

  <div class="section">
      <h1>Current Hotel Bookings</h1>
  </div>
  <div class="section">
      <g:if test="${!session.bookings}">
        <span>No Bookings Found</span>
      </g:if>
      <g:else>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Address</th>
                    <th>City, State</th>
                    <th>Check in date</th>
                    <th>Check out date</th>
                    <th>Conf number</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <g:each var="booking" in="${session.bookings}">
                <tr>
                    <td>${booking.hotel.name}</td>
                    <td>${booking.hotel.address}</td>
                    <td>${booking.hotel.city}, ${booking.hotel.state}, ${booking.hotel.country}</td>
                    <td>${booking.checkinDate}</td>
                    <td>${booking.checkoutDate}</td>
                    <td>${booking.id}</td>
                    <td><g:link action="cancelBooking" id="${booking.id}">Cancel</g:link></td>
                </tr>
                </g:each>
            </tbody>
        </table>
      </g:else>
  </div>
</body>
</html>
