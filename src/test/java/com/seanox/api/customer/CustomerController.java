package com.seanox.api.customer;

import com.seanox.apidav.ApiDavMapping;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

import static com.seanox.apidav.ApiDavMapping.Type.GET;
import static com.seanox.apidav.ApiDavMapping.Type.PUT;

// A managed bean is required.
// There are various annotations for this: e.g. @Component, @Service, @RestController, ...
@Component
public class CustomerController {

    @ApiDavMapping(type=GET, path="/customer/list.xlsx")
    public void getList(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/customer/list.xlsx")
    public void putList(InputStream output) {
    }

    @ApiDavMapping(type=GET, path="/customer/reports/statistic.xlsx")
    public void statistic(OutputStream output) {
    }

    @ApiDavMapping(type=GET, path="/customer/reports/turnover.xlsx")
    public void turnover(OutputStream output) {
    }

    @ApiDavMapping(type=GET, path="/marketing/newsletter.pptx")
    public void getNewsletter(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/marketing/newsletter.pptx")
    public void putNewsletter(InputStream output) {
    }

    @ApiDavMapping(type=GET, path="/marketing/sales.pptx")
    public void getSales(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/marketing/sales.pptx")
    public void putSales(InputStream output) {
    }
}