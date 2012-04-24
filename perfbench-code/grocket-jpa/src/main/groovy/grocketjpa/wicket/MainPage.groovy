package grocketjpa.wicket

import grocketjpa.entity.Hotel
import grocketjpa.entity.Booking
import java.util.Arrays
import java.util.List

import javax.persistence.EntityManager
import javax.persistence.Query
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

class MainPage extends TemplatePage {  

    WebMarkupContainer hotelsContainer

    MainPage() {
        setDefaultModel new CompoundPropertyModel(new PropertyModel(this, "session"))
        add new FeedbackPanel("messages")
        add new SearchForm("form", this)
        hotelsContainer = new WebMarkupContainer("hotelsContainer")
        add hotelsContainer.setOutputMarkupId(true)

        hotelsContainer.add(new WebMarkupContainer("noResultsContainer") {            
            boolean isVisible() {
                return !isHotelsVisible()
            }
        })

        def hotelsTable = new WebMarkupContainer("hotelsTable") {
            boolean isVisible() {
                return isHotelsVisible()
            }
        }

        hotelsContainer.add hotelsTable

        hotelsTable.add(new PropertyListView<Hotel>("hotels") {            
            void populateItem(ListItem<Hotel> item) {
                item.add new Label("name")
                item.add new Label("address")
                item.add new Label("city").setRenderBodyOnly(true)
                item.add new Label("state").setRenderBodyOnly(true)
                item.add new Label("country").setRenderBodyOnly(true)
                item.add new Label("zip")
                item.add(new Link<Hotel>("view", item.getModel()) {                    
                    void onClick() {
                        setResponsePage new HotelPage(getModelObject())
                    }
                })
            }
        })

        hotelsContainer.add(new Link("moreResultsLink") {            
            void onClick() {
                session.page++
                loadHotels()
            }            
            boolean isVisible() {
                def hotels = session.hotels
                return hotels != null && hotels.size() == session.pageSize
            }
        })

        if(session.bookings == null) {
            loadBookings()
        }

        add(new WebMarkupContainer("noBookingsContainer") {            
            boolean isVisible() {
                return !isBookingsVisible()
            }
        })

        def bookingsTable = new WebMarkupContainer("bookingsTable") {
            boolean isVisible() {
                return isBookingsVisible()
            }
        }

        add bookingsTable

        bookingsTable.add(new PropertyListView<Booking>("bookings") {            
            void populateItem(ListItem<Booking> item) {
                item.add new Label("hotel.name")
                item.add new Label("hotel.address")
                item.add new Label("hotel.city").setRenderBodyOnly(true)
                item.add new Label("hotel.state").setRenderBodyOnly(true)
                item.add new Label("hotel.country").setRenderBodyOnly(true)
                item.add new Label("checkinDate")
                item.add new Label("checkoutDate")
                item.add new Label("id")
                item.add(new Link<Booking>("cancel", item.getModel()) {                    
                    void onClick() {
                        def booking = modelObject
                        // logger.info("Cancel booking: {} for {}", booking.id, session.user.username);
                        def em = getEntityManager()
                        def cancelled = em.find(Booking.class, booking.id)
                        if (cancelled != null) {
                            em.remove(cancelled)
                            session.info("Booking cancelled for confirmation number " + booking.id);
                        }
                        loadBookings()
                    }
                })
            }
        })

    }

    static class SearchForm extends Form implements IAjaxIndicatorAware {

        static final pageSizes = [5, 10, 20, 50]

        MainPage mainPage

        SearchForm(id, mainPage) {
            super(id)
            this.mainPage = mainPage
            def searchField = new TextField("searchString")
            add searchField
            searchField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {                
                void onUpdate(AjaxRequestTarget target) {
                    mainPage.refreshHotelsContainer(target)
                }
            })
            add new DropDownChoice("pageSize", pageSizes)
            add(new AjaxButton("submit") {                
                void onSubmit(AjaxRequestTarget target, Form form) {
                    mainPage.refreshHotelsContainer(target)
                }
            })
        }
        
        String getAjaxIndicatorMarkupId() {
            return "spinner"
        }

    }

    void refreshHotelsContainer(AjaxRequestTarget target) {
        session.page = 0
        loadHotels()
        target.addComponent(hotelsContainer)
    }

    void loadHotels() {
        def searchString = session.searchString
        def pattern = searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%'
        def query = entityManager.createQuery("select h from Hotel h"
                + " where lower(h.name) like :pattern"
                + " or lower(h.city) like :pattern"
                + " or lower(h.zip) like :pattern"
                + " or lower(h.address) like :pattern")
        query.setParameter("pattern", pattern)
        query.maxResults = session.pageSize
        query.firstResult = session.page * session.pageSize
        session.hotels = query.resultList
    }

    boolean isHotelsVisible() {        
        def hotels = session.hotels
        return hotels != null && !hotels.empty
    }

    boolean isBookingsVisible() {        
        return !session.bookings.empty
    }

}

