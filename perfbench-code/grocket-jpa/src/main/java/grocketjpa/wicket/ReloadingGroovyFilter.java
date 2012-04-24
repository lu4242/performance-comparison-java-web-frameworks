package grocketjpa.wicket;

import java.io.IOException;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.wicket.protocol.http.WicketFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReloadingGroovyFilter extends WicketFilter {

    private static final Logger logger = LoggerFactory.getLogger(ReloadingGroovyFilter.class);        
    
    private final ReloadingGroovyClassLoader groovyClassLoader;
    private FilterConfig filterConfig;

    public ReloadingGroovyFilter() {        
        groovyClassLoader = new ReloadingGroovyClassLoader(getClass().getClassLoader());        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        super.init(filterConfig);        
    }

    @Override
    public boolean doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        if (groovyClassLoader.hasChanges()) {
            logger.info("changes to reloadable classes detected, reloading...");
            groovyClassLoader.clearCache();
            super.init(filterConfig);
        }        
        return super.doGet(request, response);
    }

    @Override
    protected ClassLoader getClassLoader() {
        return groovyClassLoader;
    }
    
}
