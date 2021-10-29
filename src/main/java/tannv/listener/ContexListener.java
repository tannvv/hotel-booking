/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.listener;

import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;
import tannv.util.PropertiesFileHelper;

public class ContexListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        //config site map
        String siteMapLocation
                = context.getInitParameter(
                        "SITEMAP_PROPERTIES_FILE_LOCATION");
        Properties siteMapProperty
                = PropertiesFileHelper.getProperties(context,
                        siteMapLocation);
        context.setAttribute("SITE_MAP", siteMapProperty);
        //config log4j
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroyed listener");
    }
}
