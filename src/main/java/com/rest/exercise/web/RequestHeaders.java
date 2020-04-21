package com.rest.exercise.web;

import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestHeaders {

    private String xToken;

    public String getXToken() {
        return xToken;
    }

    public void setXToken(String xToken) {
        this.xToken = xToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeaders that = (RequestHeaders) o;
        return Objects.equals(xToken, that.xToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xToken);
    }

    @Override
    public String toString() {
        return "RequestHeaders{" +
                "xToken='" + xToken + '\'' +
                '}';
    }
}
