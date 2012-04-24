<html>
<head>
  <meta name="layout" content="template"/>
  <g:javascript library="prototype"/>
</head>
<body>
<div class="section">
  <h1>Book Hotel</h1>
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
    <div class="label">Nightly rate:</div>
    <div class="output"><g:formatNumber number="${booking.hotel.price}" format="\$0.00"/></div>
  </div>
  <g:form action="book">
    <div class="entry">
      <div class="label">Check In Date:</div>
      <g:datePicker name="checkinDate" value="${booking.checkinDate}" precision="day" years="[2009, 2010]"/>
      <g:formmsg bean="${booking}" name="checkinDate"/>
    </div>
    <div class="entry">
      <div class="label">Check Out Date:</div>
      <g:datePicker name="checkoutDate" value="${booking.checkoutDate}" precision="day" years="[2009, 2010]"/>
      <g:formmsg bean="${booking}" name="checkoutDate"/>
    </div>
    <div class="entry">
      <div class="label">Room Preference:</div>
      <div class="input">
        <g:select name="beds" value="${booking.beds}" optionKey="key" optionValue="value"
                  from="${[1:'One king-sized bed',2:'Two double beds',3:'Three beds']}"/>
      </div>
    </div>
    <div class="entry">
      <div class="label">Smoking Preference:</div>
      <div class="input" wicket:id="smokingBorder">
        <g:radioGroup value="${booking.smoking}" name="smoking" labels="['Smoking','Non Smoking']" values="[true, false]">
          <p>${it.label} ${it.radio}</p>
        </g:radioGroup>
      </div>
    </div>
    <div class="entry">
      <div class="label">Credit Card #:</div>
      <div class="input">
        <g:formval bean="${booking}" name="creditCard"/>
      </div>
    </div>
    <div class="entry">
      <div class="label">Credit Card Name:</div>
      <div class="input">
        <g:formval bean="${booking}" name="creditCardName"/>
      </div>
    </div>
    <div class="entry">
      <div class="label">Credit Card Expiry:</div>
      <div class="input">
        <g:select name="creditCardExpiryMonth" value="${booking.creditCardExpiryMonth}" optionKey="key" optionValue="value"
                  from="${[1:'Jan',2:'Feb',3:'Mar',4:'Apr',5:'May',6:'Jun',7:'Jul',8:'Aug',9:'Sep',10:'Oct',11:'Nov',12:'Dec']}"/>
        <g:select name="creditCardExpiryYear" value="${booking.creditCardExpiryYear}" from="${2006..2010}"/>
      </div>
    </div>
    <div class="entry">
      <div class="label"></div>
      <div class="input">
        <g:submitButton value="Proceed" name="proceed"/>
        <g:submitButton value="Cancel" name="cancel"/>
      </div>
    </div>
  </g:form>
</div>
</body>
</html>
