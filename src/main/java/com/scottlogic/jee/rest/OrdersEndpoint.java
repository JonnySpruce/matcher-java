package com.scottlogic.jee.rest;

import com.scottlogic.common.matcher.OrderMatcher;
import com.scottlogic.jee.util.ThreadSafe;
import static java.util.stream.Collectors.*;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.Response.*;

@Path("/orders")
public class OrdersEndpoint {
    
    private OrderMatcher orders;

    public OrdersEndpoint()
    {
    }

    @Inject
    public OrdersEndpoint(@ThreadSafe OrderMatcher orders)
    {
        this.orders = orders;
    }
    
    @POST
    public Response addNew(ListingDto newListing)
    {
        orders.placeOrder(newListing.toListing());
        return ok().build();
    }
    
    @GET
    @Produces("application/json")
    public Response getOrders()
    {
        return ok(orders.activeOrders().map(o -> new ListingDto(o))
                .collect(toList())).build();
    }

}
