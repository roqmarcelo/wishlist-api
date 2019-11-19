package infrastructure.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import domain.customer.CustomerDAO;
import domain.customer.CustomerResourceHandler;
import domain.customer.CustomerService;
import domain.product.ProductDAO;
import domain.product.ProductResourceHandler;
import domain.product.ProductService;
import rest.RestServlet;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceServletConfig extends GuiceServletContextListener {

    private static final String URL_PATTERN = "/api/customers/*";

    @Override
    protected Injector getInjector() {
        System.out.println("getInjector()");
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                super.configureServlets();
                System.out.println("configureServlets()");
                serve(URL_PATTERN).with(RestServlet.class);
                bind(CustomerDAO.class);
                bind(CustomerService.class);
                bind(CustomerResourceHandler.class);
                bind(ProductDAO.class);
                bind(ProductService.class);
                bind(ProductResourceHandler.class);
            }
        });
    }
}