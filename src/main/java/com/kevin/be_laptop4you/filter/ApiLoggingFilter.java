package com.kevin.be_laptop4you.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "requestId";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final Set<String> IGNORED_PATHS = Set.of("/favicon.ico");

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String requestId = getOrCreateRequestId(request);
        MDC.put(REQUEST_ID, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            writeAccessLog(request, response, duration);
            MDC.remove(REQUEST_ID);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return IGNORED_PATHS.contains(request.getRequestURI());
    }

    private void writeAccessLog(
            HttpServletRequest request,
            HttpServletResponse response,
            long duration
    ) {
        int status = response.getStatus();
        String message = "{} | {} | {} | status={} | duration={}ms | ip={}";
        Object[] arguments = {
                request.getMethod(),
                buildSafePath(request),
                getUsername(request),
                status,
                duration,
                getClientIp(request)
        };

        if (status >= 500) {
            log.error(message, arguments);
        } else if (status >= 400) {
            log.warn(message, arguments);
        } else {
            log.info(message, arguments);
        }
    }

    private String buildSafePath(HttpServletRequest request) {
        // Không ghi query string để tránh vô tình lưu token hoặc dữ liệu nhạy cảm.
        return request.getRequestURI();
    }

    private String getUsername(HttpServletRequest request) {
        return request.getUserPrincipal() == null
                ? "anonymous"
                : request.getUserPrincipal().getName();
    }

    private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String getOrCreateRequestId(HttpServletRequest request) {
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        return requestId == null || requestId.isBlank()
                ? UUID.randomUUID().toString().replace("-", "")
                : requestId;
    }
}