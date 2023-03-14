package ru.regiuss.practice.dictinoary.server.configuration;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Log4j2
public class RedirectToIndexFilter implements Filter {

    private static final Set<String> ALLOWED_URLS = new HashSet<>(Arrays.asList(
            "/swagger-ui.html", "/api", "/static", "/webjars", "/swagger", "/csrf", "/v1", "/v2"
    ));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
        if (ALLOWED_URLS.stream().anyMatch(requestURI::startsWith)) {
            chain.doFilter(request, response);
            return;
        }
        request.getRequestDispatcher("/").forward(request, response);
    }
}
