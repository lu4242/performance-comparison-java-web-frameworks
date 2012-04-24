package wicketjpa.wicket;

import wicketjpa.entity.Hotel;
import wicketjpa.entity.Booking;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

public class MainPage extends TemplatePage {

    private static final List<Integer> pageSizes = Arrays.asList(5, 10, 20);

    private WebMarkupContainer hotelsContainer;

    public MainPage() {
        setDefaultModel(new CompoundPropertyModel(new PropertyModel(this, "session")));
        add(new FeedbackPanel("messages"));
        add(new SearchForm("form"));
        hotelsContainer = new WebMarkupContainer("hotelsContainer");        
        add(hotelsContainer.setOutputMarkupId(true));

        hotelsContainer.add(new WebMarkupContainer("noResultsContainer") {
            @Override
            public boolean isVisible() {
                return !isHotelsVisible();
            }
        });

        WebMarkupContainer hotelsTable = new WebMarkupContainer("hotelsTable") {
            @Override
            public boolean isVisible() {
                return isHotelsVisible();
            }
        };

        hotelsContainer.add(hotelsTable);

        hotelsTable.add(new PropertyListView<Hotel>("hotels") {
            @Override
            protected void populateItem(ListItem<Hotel> item) {
                item.add(new Label("name"));
                item.add(new Label("address"));
                item.add(new Label("city").setRenderBodyOnly(true));
                item.add(new Label("state").setRenderBodyOnly(true));
                item.add(new Label("country").setRenderBodyOnly(true));
                item.add(new Label("zip"));                
                item.add(new Link<Hotel>("view", item.getModel()) {
                    @Override
                    public void onClick() {
                        setResponsePage(new HotelPage(getModelObject()));
                    }
                });
            }
        });
        
        hotelsContainer.add(new Link("moreResultsLink") {
            @Override
            public void onClick() {
                BookingSession session = getBookingSession();
                session.setPage(session.getPage() + 1);
                loadHotels();
            }
            @Override
            public boolean isVisible() {
                List<Hotel> hotels = getBookingSession().getHotels();
                return hotels != null && hotels.size() == getBookingSession().getPageSize();
            }
        });                

        if(getBookingSession().getBookings() == null) {
            loadBookings();
        }
        
        add(new WebMarkupContainer("noBookingsContainer") {
            @Override
            public boolean isVisible() {
                return !isBookingsVisible();
            }
        });

        WebMarkupContainer bookingsTable = new WebMarkupContainer("bookingsTable") {
            @Override
            public boolean isVisible() {
                return isBookingsVisible();
            }
        };

        add(bookingsTable);

        bookingsTable.add(new PropertyListView<Booking>("bookings") {
            @Override
            protected void populateItem(ListItem<Booking> item) {                
                item.add(new Label("hotel.name"));
                item.add(new Label("hotel.address"));
                item.add(new Label("hotel.city").setRenderBodyOnly(true));
                item.add(new Label("hotel.state").setRenderBodyOnly(true));
                item.add(new Label("hotel.country").setRenderBodyOnly(true));
                item.add(new Label("checkinDate"));
                item.add(new Label("checkoutDate"));
                item.add(new Label("id"));
                item.add(new Link<Booking>("cancel", item.getModel()) {
                    @Override
                    public void onClick() {
                        Booking booking = getModelObject();
                        logger.info("Cancel booking: {} for {}", booking.getId(), getBookingSession().getUser().getUsername());
                        EntityManager em = getEntityManager();
                        Booking cancelled = em.find(Booking.class, booking.getId());
                        if (cancelled != null) {
                            em.remove(cancelled);                            
                            getSession().info("Booking cancelled for confirmation number " + booking.getId());                            
                        }
                        loadBookings();                        
                    }
                });
            }
        });
        
    }

    private class SearchForm extends Form implements IAjaxIndicatorAware {        

        public SearchForm(String id) {
            super(id);            
            TextField searchField = new TextField("searchString");
            add(searchField);
            searchField.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    refreshHotelsContainer(target);
                }                
            });
            add(new DropDownChoice("pageSize", pageSizes));
            add(new AjaxButton("submit") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form form) {                    
                    refreshHotelsContainer(target);
                }
            });
        }

        @Override
        public String getAjaxIndicatorMarkupId() {
            return "spinner";
        }

    }

    private void refreshHotelsContainer(AjaxRequestTarget target) {        
        getBookingSession().setPage(0);
        loadHotels();
        target.addComponent(hotelsContainer);        
    }

    private void loadHotels() {
        BookingSession session = getBookingSession();
        String searchString = session.getSearchString();
        String pattern = searchString == null ? "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
        Query query = getEntityManager().createQuery("select h from Hotel h"
                + " where lower(h.name) like :pattern"
                + " or lower(h.city) like :pattern"
                + " or lower(h.zip) like :pattern"
                + " or lower(h.address) like :pattern");
        query.setParameter("pattern", pattern);
        query.setMaxResults(session.getPageSize());
        query.setFirstResult(session.getPage() * session.getPageSize());
        session.setHotels(query.getResultList());
    }

    private boolean isHotelsVisible() {
        List<Hotel> hotels = getBookingSession().getHotels();
        return hotels != null && !hotels.isEmpty();
    }

    private boolean isBookingsVisible() {
        return !getBookingSession().getBookings().isEmpty();
    }
    
}

