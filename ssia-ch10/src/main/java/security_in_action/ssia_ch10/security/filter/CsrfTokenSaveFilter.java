package security_in_action.ssia_ch10.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import security_in_action.ssia_ch10.security.csrf.repository.CustomCsrfTokenRepository;

@RequiredArgsConstructor
public class CsrfTokenSaveFilter extends OncePerRequestFilter {

    private final CustomCsrfTokenRepository csrfTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (token != null) {
            csrfTokenRepository.saveToken(token, request, response);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !HttpMethod.GET.matches(request.getMethod());
    }
}
