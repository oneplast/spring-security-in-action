package security_in_action.ssia_ch9.security.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class RequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestId = httpRequest.getHeader("Request-Id");

        if (ObjectUtils.isEmpty(requestId)) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
