package com.scottlogic.jee.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import com.scottlogic.common.util.AcceptanceTest;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import static com.squareup.okhttp.RequestBody.*;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.*;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

@RunWith(Arquillian.class)
@Category(AcceptanceTest.class)
public abstract class DefaultAcceptanceTest {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @ArquillianResource 
    private URL baseUrl;

    private final OkHttpClient client = new OkHttpClient();

    @Deployment
    public static WebArchive createDeployment()
    {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.scottlogic.jee")
                .addPackages(true, "com.scottlogic.common")
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies()
                        .resolve().withTransitivity().asFile())
                .addAsWebInfResource(INSTANCE, "beans.xml");
    }
    
    protected final Response httpPostAsJson(String contextPath, Object body) 
            throws JsonProcessingException, IOException
    {
        Request postRequest = new Request.Builder()
                .url(baseUrl + contextPath)
                .post(create(MediaType.parse("application/json"),
                        MAPPER.writeValueAsString(body)))
                .build();

        return client.newCall(postRequest).execute();
    }
    
    protected final Response httpGet(String contextPath) 
            throws JsonProcessingException, IOException
    {
        Request getRequest = new Request.Builder()
                .url(baseUrl + contextPath)
                .build();

        return client.newCall(getRequest).execute();
    }

}
