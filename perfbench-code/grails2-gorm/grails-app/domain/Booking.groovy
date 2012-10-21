import java.text.DateFormat

class Booking implements Serializable {

    User user
    Hotel hotel
    Date checkinDate
    Date checkoutDate
    String creditCard
    String creditCardName
    int creditCardExpiryMonth
    int creditCardExpiryYear
    boolean smoking
    int beds

    static transients = ['total', 'nights', 'description']

    BigDecimal getTotal() {
        hotel.price.multiply(new BigDecimal(nights))
    }

    int getNights() {
        (checkoutDate.time - checkinDate.time) / 1000 / 60 / 60 / 24
    }

    String getDescription() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        hotel ? "${hotel.name}, ${df.format checkinDate} to ${df.format checkoutDate}" : null
    }

    static constraints = {
        creditCard(blank:false, matches: '^\\d*$', size:16..16)
        creditCardName(blank:false, size:3..70)
    }
    
}

