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

public class BuyingTest extends DefaultAcceptanceTest {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Test
    @RunAsClient
    public void clientCanBuyAnAssetForSale() throws Throwable
    {
        httpPostAsJson("sales", new ListingDto("ac0", 5, 245));
        httpPostAsJson("sales", new ListingDto("ac1", 2, 243));
        httpPostAsJson("sales", new ListingDto("ac2", 3, 244));
        
        httpPostAsJson("orders", new ListingDto("ac3", 3, 244));

        Response salesResponse = httpGet("sales");

        assertThat(salesResponse.code(), is(200));
        
        List<ListingDto> salesJson = MAPPER.readValue(
                salesResponse.body().string(),
                new TypeReference<List<ListingDto>>(){});
        assertThat(salesJson, containsInAnyOrder(
                new ListingDto("ac0", 5, 245), new ListingDto("ac2", 2, 244)
        ));
        
        Response ordersResponse = httpGet("orders");

        assertThat(salesResponse.code(), is(200));
        
        List<ListingDto> ordersJson = MAPPER.readValue(
                ordersResponse.body().string(),
                new TypeReference<List<ListingDto>>(){});
        assertThat(ordersJson, hasSize(0));
    }

}
