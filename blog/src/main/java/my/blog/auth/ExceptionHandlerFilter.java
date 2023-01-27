package my.blog.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.blog.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
//@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

//    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            log.error(e.getMessage());
            setErrorResponse(response, request, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpServletRequest request, Throwable ex) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        body.put("path", request.getServletPath());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
