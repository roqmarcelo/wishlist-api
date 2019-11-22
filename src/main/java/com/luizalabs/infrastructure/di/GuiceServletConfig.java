package com.luizalabs.infrastructure.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.luizalabs.domain.customer.CustomerDAO;
import com.luizalabs.domain.customer.CustomerResourceHandler;
import com.luizalabs.domain.customer.CustomerService;
import com.luizalabs.domain.product.ProductDAO;
import com.luizalabs.domain.product.ProductResourceHandler;
import com.luizalabs.domain.product.ProductService;
import com.luizalabs.rest.RestServlet;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceServletConfig extends GuiceServletContextListener {

    private static final String URL_PATTERN = "/api/customers/*";

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                super.configureServlets();
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