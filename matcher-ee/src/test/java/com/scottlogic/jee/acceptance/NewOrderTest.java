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

public class NewOrderTest extends DefaultAcceptanceTest {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Test
    @RunAsClient
    public void clientCanPostAnOrder() throws Throwable
    {
        ListingDto newAssetJson = new ListingDto("aaa", 2000, 245);

        Response postResponse = httpPostAsJson("orders", newAssetJson);

        assertThat(postResponse.code(), is(200));

        Response getResponse = httpGet("orders");

        assertThat(getResponse.code(), is(200));
        
        List<ListingDto> responseJson = MAPPER.readValue(
                getResponse.body().string(),
                new TypeReference<List<ListingDto>>(){});
        assertThat(responseJson, contains(newAssetJson));
    }

}
