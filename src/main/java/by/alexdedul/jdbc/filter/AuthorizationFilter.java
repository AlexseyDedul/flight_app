package by.alexdedul.jdbc.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static by.alexdedul.jdbc.utlis.UrlPath.LOGIN;
import static by.alexdedul.jdbc.utlis.UrlPath.REGISTRATION;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATHS = Set.of(LOGIN, REGISTRATION);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if(isPublicPath(uri) || isUserLoggedIn(servletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }
}
