package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class ServiceAffecteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAffecte.class);
        ServiceAffecte serviceAffecte1 = new ServiceAffecte();
        serviceAffecte1.setId(1L);
        ServiceAffecte serviceAffecte2 = new ServiceAffecte();
        serviceAffecte2.setId(serviceAffecte1.getId());
        assertThat(serviceAffecte1).isEqualTo(serviceAffecte2);
        serviceAffecte2.setId(2L);
        assertThat(serviceAffecte1).isNotEqualTo(serviceAffecte2);
        serviceAffecte1.setId(null);
        assertThat(serviceAffecte1).isNotEqualTo(serviceAffecte2);
    }
}
