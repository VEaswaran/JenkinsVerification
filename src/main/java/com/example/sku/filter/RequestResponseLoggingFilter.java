package com.example.sku.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * Servlet filter that captures incoming request details and outgoing response
 * details (HTTP status, status code) along with the time taken to process
 * each request, and logs them.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long timeTakenMs = System.currentTimeMillis() - startTime;
            int statusCode = wrappedResponse.getStatus();
            String statusText = resolveStatusText(statusCode);

            LOGGER.info("method={} uri={} statusCode={} status={} timeTakenMs={}",
                    request.getMethod(),
                    request.getRequestURI(),
                    statusCode,
                    statusText,
                    timeTakenMs);

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String resolveStatusText(int statusCode) {
        org.springframework.http.HttpStatus status = org.springframework.http.HttpStatus.resolve(statusCode);
        return status != null ? status.name() : "UNKNOWN";
    }
}

