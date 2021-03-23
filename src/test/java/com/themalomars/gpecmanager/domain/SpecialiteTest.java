package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class SpecialiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialite.class);
        Specialite specialite1 = new Specialite();
        specialite1.setId(1L);
        Specialite specialite2 = new Specialite();
        specialite2.setId(specialite1.getId());
        assertThat(specialite1).isEqualTo(specialite2);
        specialite2.setId(2L);
        assertThat(specialite1).isNotEqualTo(specialite2);
        specialite1.setId(null);
        assertThat(specialite1).isNotEqualTo(specialite2);
    }
}
