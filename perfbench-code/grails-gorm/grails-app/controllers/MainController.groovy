import java.util.Calendar

class MainController {

    def auth() {
        if(!session.user) {
            redirect(uri: '/')
        }
    }

    def beforeInterceptor = [action: this.&auth]

    static allowedMethods = [book: 'POST', bookSubmit: 'POST', revise: 'POST', confirmSubmit: 'POST']

    def index = {
        loadBookings()
        if(session.pageSize == null) {
            session.pageSize = 10
            session.page = 0
        }
        render(view: 'main')
    }

    def search = {
        session.page = 0
        session.pageSize = params.pageSize.toInteger()
        session.searchString = params.searchString
        loadHotels()
        render(template: 'hotels')
    }

    def nextPage = {
        session.page++
        loadHotels()
        render(view: 'main')
    }

    def viewHotel = {        
        render(view: 'hotel', model: [hotel: Hotel.get(params.id)])
    }

    def bookFlow = {
        start {            
            action {
                def booking = new Booking()
                booking.hotel = Hotel.get(params.id)
                booking.user = session.user
                def calendar = Calendar.getInstance()
                booking.checkinDate = calendar.time
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                booking.checkoutDate = calendar.time
                flow.booking = booking                
            }
            on("success").to "book"
        }
        book {            
            on("proceed") {
                def booking = flow.booking
                bindData(booking, params)
                booking.validate()
                def yesterday = Calendar.getInstance()
                yesterday.add(Calendar.DAY_OF_MONTH, -1)
                if(booking.checkinDate.before(yesterday.time)) {
                    booking.errors.rejectValue('checkinDate', null, 'Check in date must be a future date')
                }
                if(booking.checkoutDate.before(booking.checkinDate)) {
                    booking.errors.rejectValue('checkoutDate', null, 'Check out date must be later than check in date')
                }
                if(booking.hasErrors()) {
                    return error()
                }
            }.to "confirm"
            on("cancel").to "finish"
            on("error").to "book"            
        }
        confirm {
            on("confirm") {
                def booking = flow.booking
                booking.save()
                flow.message = "Thank you, ${session.user.name}, your confimation number for ${booking.hotel.name} is ${booking.id}"
                log.info "New booking: ${booking.id} for ${session.user.username}"
                this.loadBookings()
            }.to "finish"
            on("revise").to "book"
            on("cancel").to "finish"
        }
        finish {            
            render(view: '/main/main', model: [message: flow.message])
        }
    }

    def cancelBooking = {
        def booking = Booking.get(params.id)
        log.info "Cancel booking: ${booking.id} for ${session.user.username}"
        if(booking != null) {
            booking.delete()
            flash.message = "Booking cancelled for confirmation number ${booking.id}"
        }
        redirect(action: 'index')
    }

    void loadHotels() {        
        def pattern = session.searchString ? '%' + session.searchString.toLowerCase().replace('*', '%') + '%' : '%'
        session.hotels = Hotel.findAll("from Hotel h"
                + " where lower(h.name) like :pattern"
                + " or lower(h.city) like :pattern"
                + " or lower(h.zip) like :pattern"
                + " or lower(h.address) like :pattern", 
                [pattern: pattern, max: session.pageSize, offset: session.pageSize * session.page]
        )
    }

    void loadBookings() {        
        session.bookings = Booking.findAll("from Booking b"
                + " where b.user.id = :id order by b.checkinDate", [id: session.user.id])
    }
    
}
