package se.ystromm.springboot.webjars;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.google.common.base.Optional;

public class WebjarVersionTest {

    @Test
    public void non_existing_group_should_return_absent() {
        assertThat(WebjarVersion.webjarVersion("doesNotExist", "d3js"), equalTo(Optional.<String> absent()));
    }

    @Test
    public void non_existing_lib_should_return_absent() {
        assertThat(WebjarVersion.webjarVersion("org.webjars", "doesNotExist"), equalTo(Optional.<String> absent()));
    }

    @Test
    public void should_return_version() {
        assertThat(WebjarVersion.webjarVersion("org.webjars", "d3js"), equalTo(Optional.of("3.5.3")));
    }

}
