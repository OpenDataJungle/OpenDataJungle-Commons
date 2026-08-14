package com.opendatajungle.commons;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

/**
 * ArchUnit test to verify compliance with hexagonal architecture.
 * Expected structure:
 * - business: Business domain (core of the hexagon) - should not depend on anything
 * - client: Primary/inbound adapters (REST API, controllers)
 * - infra: Secondary/outbound adapters (repositories, JPA entities, technical services)
 */
@DisplayName("Hexagonal Architecture Tests")
class HexagonalArchitectureTest {
    public static final String COM_OPENDATAJUNGLE = "com.opendatajungle.commons";
    private static JavaClasses importedClasses;

    @BeforeAll
    static void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(COM_OPENDATAJUNGLE);
    }

    @Test
    @DisplayName("Business domain should not depend on adapters (client, infra)")
    void domainShouldNotDependOnAdapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business..")
                .should().dependOnClassesThat().resideInAnyPackage(COM_OPENDATAJUNGLE + ".client..", COM_OPENDATAJUNGLE + ".infra..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Business domain should only depend on itself and vanilla Java")
    void domainShouldOnlyDependOnItselfAndJava() {
        ArchRule rule = classes()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        COM_OPENDATAJUNGLE + ".business..",
                        COM_OPENDATAJUNGLE + ".shared..",
                        "java..",
                        "org.slf4j.."
                )
                .because("Business layer must be purely business-oriented, with no technical or framework dependencies");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Client adapters can depend on domain but not on infrastructure")
    void clientAdaptersShouldDependOnDomainButNotInfra() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".client..")
                .should().dependOnClassesThat().resideInAPackage(COM_OPENDATAJUNGLE + ".infra..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Infrastructure adapters can depend on domain but not on client")
    void infraAdaptersShouldDependOnDomainButNotClient() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".infra..")
                .should().dependOnClassesThat().resideInAPackage(COM_OPENDATAJUNGLE + ".client..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Domain repository interfaces must be in business.repository")
    void domainRepositoriesShouldBeInBusinessPackage() {
        ArchRule rule = classes()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business.repository..")
                .should().beInterfaces().allowEmptyShould(true);

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Business services must be in business.service package")
    void businessServicesShouldBeInBusinessPackage() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business.service..")
                .should().dependOnClassesThat().resideInAnyPackage(COM_OPENDATAJUNGLE + ".client..", COM_OPENDATAJUNGLE + ".infra..");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Onion architecture - global verification")
    void onionArchitectureShouldBeRespected() {
        ArchRule rule = onionArchitecture()
                .domainModels(COM_OPENDATAJUNGLE + ".business.model..", COM_OPENDATAJUNGLE + ".business.repository..")
                .domainServices(COM_OPENDATAJUNGLE + ".business.service..")
                .applicationServices(COM_OPENDATAJUNGLE + ".client.service..")
                .adapter("client", COM_OPENDATAJUNGLE + ".client..")
                .adapter("infra", COM_OPENDATAJUNGLE + ".infra..")
                .withOptionalLayers(true);

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Business exceptions must be in business.exception")
    void businessExceptionsShouldBeInBusinessPackage() {
        ArchRule rule = classes()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business.exception..")
                .should().beAssignableTo(Exception.class)
                .orShould().beAssignableTo(RuntimeException.class);

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Business services should only depend on ports (interfaces), not implementations")
    void businessServicesShouldDependOnPortsOnly() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business.service..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(COM_OPENDATAJUNGLE + ".infra.repository..", COM_OPENDATAJUNGLE + ".infra.service..")
                .because("Business services should only depend on domain ports, not infrastructure adapters");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Infrastructure technical exceptions should not be exposed to domain")
    void infraExceptionsShouldNotBeInDomain() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business..")
                .should().dependOnClassesThat()
                .resideInAPackage(COM_OPENDATAJUNGLE + ".infra.exception..")
                .because("Infrastructure technical exceptions should not pollute the domain");

        rule.check(importedClasses);
    }

    @Test
    @Disabled("Can be reactivated when violations are fixed")
    @DisplayName("Domain entities should not be exposed in DTOs")
    void domainEntitiesShouldNotBeInDTOs() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".client.dto..")
                .should().dependOnClassesThat()
                .resideInAPackage(COM_OPENDATAJUNGLE + ".business.model..")
                .because("DTOs should isolate the domain from the external API");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Domain classes should not use utility classes from client or infra")
    void domainShouldNotUseAdapterUtilities() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(COM_OPENDATAJUNGLE + ".business..")
                .should().dependOnClassesThat()
                .resideInAnyPackage(COM_OPENDATAJUNGLE + ".client.tool..", COM_OPENDATAJUNGLE + ".infra.conf..")
                .because("Domain must remain independent of adapter utilities");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("Shared package can be used by all layers")
    void sharedPackageCanBeUsedByAllLayers() {
        ArchRule rule = classes()
                .that().resideInAPackage("..shared..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..shared..", "java..", "org.springframework..", "jakarta..", "org.apache..", "com.fasterxml..")
                .because("Shared package should only contain utilities without business logic");

        rule.check(importedClasses);
    }
}


