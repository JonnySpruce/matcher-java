package com.scottlogic.jee.acceptance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.jee.rest.ListingDto;
import com.squareup.okhttp.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.junit.*;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SellingTest extends DefaultAcceptanceTest {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Test
    @RunAsClient
    public void clientCanSellAnAssetThatHasActiveOrders() throws Throwable
    {
        httpPostAsJson("orders", new ListingDto("ac0", 5, 243));
        httpPostAsJson("orders", new ListingDto("ac1", 2, 245));
        httpPostAsJson("orders", new ListingDto("ac2", 3, 244));
        
        httpPostAsJson("sales", new ListingDto("ac3", 3, 244));

        Response ordersResponse = httpGet("orders");

        assertThat(ordersResponse.code(), is(200));
        
        List<ListingDto> salesJson = MAPPER.readValue(
                ordersResponse.body().string(),
                new TypeReference<List<ListingDto>>(){});
        assertThat(salesJson, containsInAnyOrder(
                new ListingDto("ac0", 5, 243), new ListingDto("ac2", 2, 244)
        ));
        
        Response salesResponse = httpGet("sales");

        assertThat(salesResponse.code(), is(200));
        
        List<ListingDto> ordersJson = MAPPER.readValue(
                salesResponse.body().string(),
                new TypeReference<List<ListingDto>>(){});
        assertThat(ordersJson, hasSize(0));
    }

}
