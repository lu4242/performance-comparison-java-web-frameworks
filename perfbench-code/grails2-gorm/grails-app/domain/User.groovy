class User implements Serializable {

    String username
    String password
    String name    

    static constraints = {
        username(blank:false, size:5..15, unique:true)
        password(blank:false, size:5..15)
        name(blank:false, maxSize:100)        
    }

}
