package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class DistinctionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Distinction.class);
        Distinction distinction1 = new Distinction();
        distinction1.setId(1L);
        Distinction distinction2 = new Distinction();
        distinction2.setId(distinction1.getId());
        assertThat(distinction1).isEqualTo(distinction2);
        distinction2.setId(2L);
        assertThat(distinction1).isNotEqualTo(distinction2);
        distinction1.setId(null);
        assertThat(distinction1).isNotEqualTo(distinction2);
    }
}
