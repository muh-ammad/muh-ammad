package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class ServiceFrequenteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceFrequente.class);
        ServiceFrequente serviceFrequente1 = new ServiceFrequente();
        serviceFrequente1.setId(1L);
        ServiceFrequente serviceFrequente2 = new ServiceFrequente();
        serviceFrequente2.setId(serviceFrequente1.getId());
        assertThat(serviceFrequente1).isEqualTo(serviceFrequente2);
        serviceFrequente2.setId(2L);
        assertThat(serviceFrequente1).isNotEqualTo(serviceFrequente2);
        serviceFrequente1.setId(null);
        assertThat(serviceFrequente1).isNotEqualTo(serviceFrequente2);
    }
}
