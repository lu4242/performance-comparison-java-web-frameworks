<html>
<head>
  <meta name="layout" content="template"/>
</head>
<body>
  <div class="section">
    <h1>Confirm Hotel Booking</h1>
  </div>
  <div class="section">
    <div class="entry">
      <div class="label">Name:</div>
      <div class="output">${booking.hotel.name}</div>
    </div>
    <div class="entry">
      <div class="label">Address:</div>
      <div class="output">${booking.hotel.address}</div>
    </div>
    <div class="entry">
      <div class="label">City, State:</div>
      <div class="output">${booking.hotel.city}, ${booking.hotel.state}</div>
    </div>
    <div class="entry">
      <div class="label">Zip:</div>
      <div class="output">${booking.hotel.zip}</div>
    </div>
    <div class="entry">
      <div class="label">Country:</div>
      <div class="output">${booking.hotel.country}</div>
    </div>
    <div class="entry">
      <div class="label">Total payment:</div>
      <div class="output"><g:formatNumber number="${booking.total}" format="\$0.00"/></div>
    </div>
    <div class="entry">
      <div class="label">Check In Date:</div>
      <div class="output">${booking.checkinDate}</div>
    </div>
    <div class="entry">
      <div class="label">Check Out Date:</div>
      <div class="output">${booking.checkoutDate}</div>
    </div>
    <div class="entry">
      <div class="label">Credit Card #:</div>
      <div class="output">${booking.creditCard}</div>
    </div>
    <g:form action="book">
      <div class="entry">
        <div class="label"></div>
        <div class="input">
          <g:submitButton value="Confirm" name="confirm"/>
          <g:submitButton value="Revise" name="revise"/>
          <g:submitButton value="Cancel" name="cancel"/>
        </div>
      </div>
    </g:form>
  </div>
</body>
</html>
