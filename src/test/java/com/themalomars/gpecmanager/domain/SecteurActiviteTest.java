package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class SecteurActiviteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecteurActivite.class);
        SecteurActivite secteurActivite1 = new SecteurActivite();
        secteurActivite1.setId(1L);
        SecteurActivite secteurActivite2 = new SecteurActivite();
        secteurActivite2.setId(secteurActivite1.getId());
        assertThat(secteurActivite1).isEqualTo(secteurActivite2);
        secteurActivite2.setId(2L);
        assertThat(secteurActivite1).isNotEqualTo(secteurActivite2);
        secteurActivite1.setId(null);
        assertThat(secteurActivite1).isNotEqualTo(secteurActivite2);
    }
}
