package com.themalomars.gpecmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.themalomars.gpecmanager.web.rest.TestUtil;

public class OperationExterieurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationExterieur.class);
        OperationExterieur operationExterieur1 = new OperationExterieur();
        operationExterieur1.setId(1L);
        OperationExterieur operationExterieur2 = new OperationExterieur();
        operationExterieur2.setId(operationExterieur1.getId());
        assertThat(operationExterieur1).isEqualTo(operationExterieur2);
        operationExterieur2.setId(2L);
        assertThat(operationExterieur1).isNotEqualTo(operationExterieur2);
        operationExterieur1.setId(null);
        assertThat(operationExterieur1).isNotEqualTo(operationExterieur2);
    }
}
