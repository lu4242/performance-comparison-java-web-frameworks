package swicketjpa.wicket

import java.util.Arrays
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.IAjaxIndicatorAware
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.PropertyListView
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.model.CompoundPropertyModel
import org.apache.wicket.model.PropertyModel
import swicketjpa.entity.Booking
import swicketjpa.entity.Hotel

object MainPage {
  val pageSizes = Arrays.asList(5, 10, 20)
}

class MainPage extends TemplatePage {
  setDefaultModel(new CompoundPropertyModel(new PropertyModel(this, "session")))
  add(new FeedbackPanel("messages"))
  add(new Form("form") with IAjaxIndicatorAware {
    val searchField = new TextField("searchString")
    add(searchField)
    searchField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
      override def onUpdate(target: AjaxRequestTarget) = refreshHotelsContainer(target)
    })
    add(new DropDownChoice("pageSize", MainPage.pageSizes))
    add(new AjaxButton("submit") {
      override def onSubmit(target: AjaxRequestTarget, form: Form[_]) = refreshHotelsContainer(target)      
    })
    override def getAjaxIndicatorMarkupId = "spinner"
  })
  val hotelsContainer = new WebMarkupContainer("hotelsContainer")
  add(hotelsContainer.setOutputMarkupId(true))
  hotelsContainer.add(new WebMarkupContainer("noResultsContainer") {
    override def isVisible = !isHotelsVisible()
  })
  val hotelsTable = new WebMarkupContainer("hotelsTable") {
    override def isVisible = isHotelsVisible()      
  }
  hotelsContainer.add(hotelsTable)
  hotelsTable.add(new PropertyListView[Hotel]("hotels") {
    override def populateItem(item: ListItem[Hotel] ) {
      item.add(new Label("name"))
      item.add(new Label("address"))
      item.add(new Label("city").setRenderBodyOnly(true))
      item.add(new Label("state").setRenderBodyOnly(true))
      item.add(new Label("country").setRenderBodyOnly(true))
      item.add(new Label("zip"))
      item.add(new Link[Hotel]("view", item.getModel()) {
        override def onClick = setResponsePage(new HotelPage(getModelObject()))
      })
    }
  })
  hotelsContainer.add(new Link("moreResultsLink") {
    override def onClick = {
      val session = getBookingSession()
      session.page = session.page + 1
      loadHotels()
    }
    override def isVisible: Boolean = {
      val hotels = getBookingSession().hotels
      return hotels != null && hotels.size == getBookingSession().pageSize
    }
  })
  if(getBookingSession().bookings == null) loadBookings()
  add(new WebMarkupContainer("noBookingsContainer") {
    override def isVisible = !isBookingsVisible()
  })
  val bookingsTable = new WebMarkupContainer("bookingsTable") {
    override def isVisible = isBookingsVisible()
  }
  add(bookingsTable)
  bookingsTable.add(new PropertyListView[Booking]("bookings") {
    override def populateItem(item: ListItem[Booking]) {
      item.add(new Label("hotel.name"))
      item.add(new Label("hotel.address"))
      item.add(new Label("hotel.city").setRenderBodyOnly(true))
      item.add(new Label("hotel.state").setRenderBodyOnly(true))
      item.add(new Label("hotel.country").setRenderBodyOnly(true))
      item.add(new Label("checkinDate"))
      item.add(new Label("checkoutDate"))
      item.add(new Label("id"))
      item.add(new Link[Booking]("cancel", item.getModel()) {
        override def onClick = {
          val booking = getModelObject()
          logger.info("Cancel booking: {} for {}", booking.id, getBookingSession().user.username)
          val em = getEntityManager()
          val cancelled = em.find(classOf[Booking], booking.id)
          if (cancelled != null) {
            em.remove(cancelled)
            getSession().info("Booking cancelled for confirmation number " + booking.id)
          }
          loadBookings()
        }
      })
    }
  })

  def refreshHotelsContainer(target: AjaxRequestTarget) = {
    getBookingSession().page = 0
    loadHotels()
    target.addComponent(hotelsContainer)
  }
                
  def loadHotels() = {
    val session = getBookingSession()
    val searchString = session.searchString
    val pattern = 
      if(searchString == null) "%"
      else "%" + searchString.toLowerCase().replace('*', '%') + "%"
    val query = getEntityManager().createQuery("select h from Hotel h"
            + " where lower(h.name) like :pattern"
            + " or lower(h.city) like :pattern"
            + " or lower(h.zip) like :pattern"
            + " or lower(h.address) like :pattern")
    query.setParameter("pattern", pattern)
    query.setMaxResults(session.pageSize)
    query.setFirstResult(session.page * session.pageSize)
    session.hotels = query.getResultList().asInstanceOf[java.util.List[Hotel]]
  }

  def isHotelsVisible(): Boolean = {
    val hotels = getBookingSession().hotels
    return hotels != null && !hotels.isEmpty
  }

  def isBookingsVisible() = !getBookingSession().bookings.isEmpty

}
