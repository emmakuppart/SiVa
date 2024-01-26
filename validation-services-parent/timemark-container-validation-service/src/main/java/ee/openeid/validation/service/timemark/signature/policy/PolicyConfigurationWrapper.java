/*
 * Copyright 2017 - 2024 Riigi Infosüsteemi Amet
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ee.openeid.validation.service.timemark.signature.policy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import ee.openeid.siva.validation.service.signature.policy.properties.ConstraintDefinedPolicy;

import org.digidoc4j.Configuration;

@Getter @Setter
@AllArgsConstructor
public class PolicyConfigurationWrapper {
    private Configuration configuration;
    private ConstraintDefinedPolicy policy;
}
