package com.github.ystromm.springboot.webjars;

import static com.github.ystromm.springboot.webjars.WebjarVersion.webjarVersion;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(WebjarVersionFilter.class);

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
            log.debug("Forwarding to requestURI={} to requestURIWithVersion={}", requestURI, requestURIWithVersion);
            request.getRequestDispatcher(requestURIWithVersion.get()).forward(request, response);
        } else {
            log.debug("Chaining");
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
