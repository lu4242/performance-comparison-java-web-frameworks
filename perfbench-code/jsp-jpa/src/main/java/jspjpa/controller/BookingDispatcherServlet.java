/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspjpa.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lu4242
 */
public class BookingDispatcherServlet extends HttpServlet
{
    private ServletConfig servletConfig;
    
    public void init(ServletConfig servletConfig) throws ServletException
    {
        this.servletConfig = servletConfig;
    }    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //super.doGet(req, resp);
        BookingRequestServiceLocator serviceLocator = new BookingRequestServiceLocator();
        try
        {
            serviceLocator.init(req, resp, this.servletConfig.getServletContext());
            
            String servletPath = req.getServletPath();
            String pathInfo = req.getPathInfo();

            if (servletPath.endsWith("home.do"))
            {
                LoginController controller = new LoginController();
                controller.doGetHome(req, resp, serviceLocator);
                return;
            }
            else if (servletPath.endsWith("logout.do"))
            {
                MainController controller = new MainController();
                controller.doGetLogout(req, resp);
                return;
            }
            else if (servletPath.endsWith("main.do"))
            {
                if (req.getParameterMap().containsKey("action"))
                {
                    MainController controller = new MainController();
                    controller.doGetNextPage(req, resp, serviceLocator);
                    return;
                }
                else
                {
                    MainController controller = new MainController();
                    controller.doGetMain(req, resp, serviceLocator);
                    return;
                }
            }
            else if (servletPath.endsWith("hotel.do"))
            {
                HotelController controller = new HotelController();
                controller.doGetHotel(req, resp, serviceLocator);
                return;
            }
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        finally
        {
            serviceLocator.destroy(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        //super.doPost(req, resp);
        BookingRequestServiceLocator serviceLocator = new BookingRequestServiceLocator();
        try
        {
            serviceLocator.init(req, resp, this.servletConfig.getServletContext());
            
            String servletPath = req.getServletPath();
            String pathInfo = req.getPathInfo();

            if (servletPath.endsWith("home.do"))
            {
                LoginController controller = new LoginController();
                controller.doPostLogin(req, resp, serviceLocator);
                return;
            }
            else if (servletPath.endsWith("main.do"))
            {
                if (req.getParameterMap().containsKey("cancelId"))
                {
                    MainController controller = new MainController();
                    controller.doPostCancel(req, resp, serviceLocator);
                    return;
                }
                else if (req.getParameterMap().containsKey("action"))
                {
                }
                else if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With")))
                {
                    MainController controller = new MainController();
                    controller.doPostSearchAjax(req, resp, serviceLocator);
                    return;
                }
                else
                {
                    MainController controller = new MainController();
                    controller.doPostSearch(req, resp, serviceLocator);
                    return;
                }
            }
            else if (servletPath.endsWith("hotel.do"))
            {
                if (req.getParameterMap().containsKey("book"))
                {
                    HotelController controller = new HotelController();
                    controller.doPostBook(req, resp, serviceLocator);
                    return;
                }
                else if (req.getParameterMap().containsKey("cancel"))
                {
                    HotelController controller = new HotelController();
                    controller.doPostCancel(req, resp, serviceLocator);
                    return;
                }
            }
            else if (servletPath.endsWith("book.do"))
            {
                if (req.getParameterMap().containsKey("cancel"))
                {
                    BookController controller = new BookController();
                    controller.doPostCancel(req, resp, serviceLocator);
                    return;
                }
                else if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With")) &&
                        req.getParameterMap().containsKey("val"))
                {
                    BookController controller = new BookController();
                    controller.doPostValidateAjax(req, resp, serviceLocator);
                    return;
                }
                else
                {
                    BookController controller = new BookController();
                    controller.doPostProceed(req, resp, serviceLocator);
                    return;
                }
            }
            else if (servletPath.endsWith("confirm.do"))
            {
                if (req.getParameterMap().containsKey("cancel"))
                {
                    ConfirmController controller = new ConfirmController();
                    controller.doPostCancel(req, resp, serviceLocator);
                    return;
                }
                else if (req.getParameterMap().containsKey("revise"))
                {
                    ConfirmController controller = new ConfirmController();
                    controller.doPostRevise(req, resp, serviceLocator);
                    return;
                }
                else if (req.getParameterMap().containsKey("confirm"))
                {
                    ConfirmController controller = new ConfirmController();
                    controller.doPostConfirm(req, resp, serviceLocator);
                    return;
                }
            }
            
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        finally
        {
            serviceLocator.destroy(req, resp);
        }
    }

}
