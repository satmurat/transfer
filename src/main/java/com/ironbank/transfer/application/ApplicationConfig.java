package com.ironbank.transfer.application;

import com.ironbank.transfer.exception.WebApplicationExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages(true, "com.ironbank.transfer");
        register(new AppBinder());
        register(WebApplicationExceptionMapper.class);
    }
}