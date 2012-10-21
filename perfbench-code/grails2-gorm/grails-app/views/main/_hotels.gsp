<div class="section" id="hotels">
    <g:if test="${!session.hotels}">
      <span>No Hotels Found</span>
    </g:if>
    <g:else>
      <table>
          <thead>
              <tr>
                  <th>Name</th>
                  <th>Address</th>
                  <th>City, State</th>
                  <th>Zip</th>
                  <th>Action</th>
              </tr>
          </thead>
          <tbody>
              <g:each var="hotel" in="${session.hotels}">
              <tr>
                  <td>${hotel.name}</td>
                  <td>${hotel.address}</td>
                  <td>${hotel.city}, ${hotel.state}, ${hotel.country}</td>
                  <td>${hotel.zip}</td>
                  <td><g:link action="viewHotel" id="${hotel.id}">View Hotel</g:link></td>
              </tr>
              </g:each>
          </tbody>
      </table>
      <g:if test="${session.hotels.size == session.pageSize}">
        <a href="${createLink(action: 'nextPage')}">More results</a>
      </g:if>
    </g:else>
</div>
