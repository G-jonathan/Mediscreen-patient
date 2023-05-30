package com.openclassroomsProject.Mediscreenpatient.model;

import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for all model class found in Mediscreenpatient/model package.
 * {@link Patient}
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
class PatientTest {
    private static final String POJO_PACKAGE = "com.openclassroomsProject.Mediscreenpatient.model";

    @Test
    public void testPojoNote() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
        validator.validate(POJO_PACKAGE, new FilterPackageInfo());
    }
}