grails.mime.file.extensions=false
grails.views.default.codec="none"
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"
// grails.enable.native2ascii=true
grails.serverURL = "http://localhost:8080/${appName}"

log4j = {
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '[%t] %p [%c] %m%n')
    }
    root {
        error 'stdout'
    }    
    info 'grails.app'
    // debug 'org.hibernate.SQL'   
}
