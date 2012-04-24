package grocketjpa.wicket;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReloadingGroovyClassLoader extends URLClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(ReloadingGroovyClassLoader.class);
        
    private long timeLastReloaded;
    private final GroovyClassLoader groovyClassLoader;

    public ReloadingGroovyClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
        timeLastReloaded = new Date().getTime();
        groovyClassLoader = new GroovyClassLoader(parent);
    }

    @Override
    public Class loadClass(final String name) throws ClassNotFoundException {
        Class clazz = findLoadedClass(name);
        if (clazz != null) {            
            return clazz;
        }
        if (name.startsWith("grocketjpa.wicket")) {
            try {
                clazz = groovyClassLoader.loadClass(name);
            } catch (Exception e) {
                // logger.warn("not found: {}", e.getMessage());
            }
        }
        if (clazz == null && getParent() != null) {
            clazz = getParent().loadClass(name);            
        }
        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }

    private boolean isGroovySourceModified(final Class clazz) {
        final String resourceName = clazz.getName().replaceAll("\\.", "/") + ".groovy";
        final URL resource = groovyClassLoader.getResource(resourceName);
        if(resource == null) {
            return false;
        }
        final File file;
        try {
            file = new File(resource.toURI());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }        
        if(file.lastModified() > timeLastReloaded) {
            logger.debug("source recently modified for: {}", clazz);            
            return true;
        }
        return false;
    }
    
    protected void clearCache() {
        groovyClassLoader.clearCache();
        timeLastReloaded = new Date().getTime();        
    }

    public boolean hasChanges() {
        for(final Class clazz : groovyClassLoader.getLoadedClasses()) {
            if(isGroovySourceModified(clazz)) {
                return true;
            }
        }
        return false;
    }

}
