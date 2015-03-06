package se.ystromm.springboot.webjars;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebjarVersionFilterTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private FilterChain chain;
    @Mock
    private ServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Test
    public void doFilter_should_chain_doFilter() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/index.html");
        new WebjarVersionFilter().doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
        verifyNoMoreInteractions();
    }
    
    @Test
    public void doFilter_should_chain_do_filter() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/webjars/angularjs/angular.js");
        new WebjarVersionFilter().doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
        verifyNoMoreInteractions();
    }
    
    @Test
    public void doFilter_should_getRequestDispatcher() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/webjars/d3js/d3.min.js");
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        new WebjarVersionFilter().doFilter(request, response, chain);
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("webjars/d3js/3.5.3/d3.min.js");
        verifyNoMoreInteractions();
    }

    private void verifyNoMoreInteractions() {
        verify(request).getRequestURI();
        Mockito.verifyNoMoreInteractions(chain);
        Mockito.verifyNoMoreInteractions(response);
        Mockito.verifyNoMoreInteractions(request);
        Mockito.verifyNoMoreInteractions(requestDispatcher);
    }
}
