package se.ystromm.springboot.webjars;

import static se.ystromm.springboot.webjars.WebjarVersion.webjarVersion;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

/**
 * 
 * Partially stolen with pride from @author olle.hallin@crisp.se and bazaar.
 * 
 * @author mac
 */
@WebFilter(urlPatterns = "/*")
@Component
public class WebjarVersionFilter implements Filter {
    final Pattern requestUriPattern = Pattern.compile("^(/webjars/)([\\w-]+)(/\\D.*)$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        final Optional<String> requestURIWithVersion = requestURIWithVersion(requestURI);
        if (requestURIWithVersion.isPresent()) {
            request.getRequestDispatcher(requestURIWithVersion.get()).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private Optional<String> requestURIWithVersion(CharSequence requestURI) {
        final Matcher matcher = requestUriPattern.matcher(requestURI);
        if (matcher.find()) {
            final String library = matcher.group(2);
            final Optional<String> version = webjarVersion(WebjarVersion.DEFAULT_MAVEN_GROUPS[0], library);
            if (version.isPresent()) {
                return Optional.of(matcher.group(1) + library + "/" + version.get()
                        + matcher.group(3));
            }
        }
        return Optional.absent();
    }

    @Override
    public void destroy() {
        // empty
    }
}
