package se.ystromm.springboot.webjars;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.base.Optional;

public class VersionsTest {

    @Test
    public void non_existing_group_should_return_absent() {
        assertThat(new Versions().version("doesNotExist", "d3js"), equalTo(Optional.<String> absent()));
    }

    @Test
    public void non_existing_lib_should_return_absent() {
        assertThat(new Versions().version("org.webjars", "doesNotExist"), equalTo(Optional.<String> absent()));
    }

    @Test
    public void should_return_version() {
        assertThat(new Versions().version("org.webjars", "d3js"), equalTo(Optional.of("3.5.3")));
    }

}
