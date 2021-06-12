package com.seanox.api.customer;

import com.seanox.apidav.ApiDavFilter;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

// A managed bean is required.
// There are various annotations for this: e.g. @Component, @Service, @RestController, ...
@Component
public class CustomerController {

    @ApiDavFilter.GET(path="/customer/list.xlsx")
    public void getList(OutputStream output) {
    }
    @ApiDavFilter.PUT(path="/customer/list.xlsx")
    public void putList(InputStream output) {
    }

    @ApiDavFilter.GET(path="/customer/reports/statistic.xlsx")
    public void statistic(OutputStream output) {
    }

    @ApiDavFilter.GET(path="/customer/reports/turnover.xlsx")
    public void turnover(OutputStream output) {
    }

    @ApiDavFilter.GET(path="/marketing/newsletter.pptx")
    public void getNewsletter(OutputStream output) {
    }
    @ApiDavFilter.PUT(path="/marketing/newsletter.pptx")
    public void putNewsletter(InputStream output) {
    }

    @ApiDavFilter.GET(path="/marketing/sales.pptx")
    public void getSales(OutputStream output) {
    }
    @ApiDavFilter.PUT(path="/marketing/sales.pptx")
    public void putSales(InputStream output) {
    }
}