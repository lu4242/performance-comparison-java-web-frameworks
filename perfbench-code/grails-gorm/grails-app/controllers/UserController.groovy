class UserController {
    
    def auth() {
        if(!session.user) {
            redirect(uri: '/')
        }
    }

    def beforeInterceptor = [action: this.&auth, except:["login", "register", "cancel", 'logout']]

    static allowedMethods = [login: 'POST', save: 'POST', savePassword: 'POST']

    def login = {
        def user = User.findByUsernameAndPassword(params.username, params.password)
        if(user) {
            session.user = user
            log.info 'Login succeeded'
            flash.message = "Welcome, ${user.name}"
            redirect(controller: 'main')
        } else {
            log.error 'Login failed'
            flash.message = 'Login failed'
            render(view: '/home', model: params)
        }
    }

    def register = {
        render(view: 'register', model: ['user': new User()])
    }

    def cancel = {
        redirect(controller: 'main')
    }

    def save = {
        def user = new User(params)
        if(user.password != params.verify) {
            user.errors.rejectValue("password", null, "Passwords don't match")
        }
        if(!user.hasErrors() && user.save()) {            
            flash.message = "Successfully registered as ${user.username}"
            redirect(uri:'/')
        } else {
            render(view: 'register', model: [user: user, verify: params.verify])
        }
    }

    def password = {
        render(view: 'password', model: [password: '', verify: ''])
    }

    def savePassword = {        
        def password = params.password        
        if(password == null || password.size() < 5 || password.size() > 15) {
            flash.message = 'Password must be between 5 and 15 characters long'
            render(view: 'password', model: params)
        } else if(password != params.verify) {
            flash.message = 'Passwords do not match'
            render(view: 'password', model: params)
        } else {
            def user = session.user
            user.password = password            
            if(user.merge()) {
                flash.message = 'Password updated'
                redirect(controller: 'main')
            } else {
                flash.message = null
                render(view: 'password', model: params + [user: user])
            }
        }
    }

    def logout = {
        session.invalidate()
        redirect(uri:'/')
    }

}
