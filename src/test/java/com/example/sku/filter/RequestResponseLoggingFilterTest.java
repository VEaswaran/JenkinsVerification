package com.example.sku.filter;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RequestResponseLoggingFilterTest {

    private final RequestResponseLoggingFilter filter = new RequestResponseLoggingFilter();

    @Test
    void logsSuccessfulRequestAndPreservesResponseStatus() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/sku/SKU1001");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
        assertEquals(200, response.getStatus());
    }

    @Test
    void logsNotFoundRequestStatus() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/sku/SKU9999");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = (req, res) -> ((jakarta.servlet.http.HttpServletResponse) res).setStatus(404);

        filter.doFilter(request, response, chain);

        assertEquals(404, response.getStatus());
    }
}

