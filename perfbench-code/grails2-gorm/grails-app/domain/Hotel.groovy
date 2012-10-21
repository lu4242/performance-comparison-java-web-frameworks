class Hotel implements Serializable {

    String name
    String address
    String city
    String state
    String zip
    String country
    BigDecimal price

    static constraints = {
        name(blank:false, maxSize:50)
        address(blank:false, maxSize:100)
        city(blank:false, maxSize:40)
        state(blank:false, size:2..10)
        zip(blank:false, size:4..6)
        country(blank:false, size:2..40)
        price(min:BigDecimal.ZERO, scale:2)
    }
    
}
