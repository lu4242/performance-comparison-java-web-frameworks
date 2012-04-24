class UrlMappings {

    static mappings = {
      "/$controller/$action?/$id?"{}
      "/"(view:"/home")
      "500"(view:'/error')
    }
    
}
