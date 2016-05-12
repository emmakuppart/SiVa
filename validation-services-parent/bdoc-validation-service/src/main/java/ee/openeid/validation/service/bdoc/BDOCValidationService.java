package ee.openeid.validation.service.bdoc;

import ee.openeid.siva.validation.document.ValidationDocument;
import ee.openeid.siva.validation.service.ValidationService;
import eu.europa.esig.dss.tsl.TrustedListsCertificateSource;
import ee.openeid.siva.validation.document.report.QualifiedReport;
import ee.openeid.siva.validation.service.bdoc.report.qualified.builder.QualifiedReportBuilder;
import org.digidoc4j.Configuration;
import org.digidoc4j.Container;
import org.digidoc4j.ContainerBuilder;
import org.digidoc4j.ValidationResult;
import org.digidoc4j.impl.bdoc.tsl.TSLCertificateSourceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class BDOCValidationService implements ValidationService {

    private TrustedListsCertificateSource trustedListSource;

    private Configuration configuration;

    @Override
    public BDOCValidationResult validateDocument(ValidationDocument validationDocument) {

        initConfiguration();

        InputStream containerInputStream = new ByteArrayInputStream(validationDocument.getBytes());

        Container container = ContainerBuilder.
                aContainer().
                withConfiguration(configuration).
                fromStream(containerInputStream).
                build();

        ValidationResult validationResult = container.validate();
        Date validationTime = new Date();

        QualifiedReportBuilder reportBuilder = new QualifiedReportBuilder(container, validationDocument.getName(), validationTime);
        QualifiedReport qualifiedReport = reportBuilder.build();

        BDOCValidationResult bdocValidationResult = new BDOCValidationResult(validationResult);
        bdocValidationResult.setQualifiedReport(qualifiedReport);

        return bdocValidationResult;
    }

    public void initConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();

            TSLCertificateSourceImpl tslCertificateSource = new TSLCertificateSourceImpl();
            trustedListSource.getCertificates().stream().forEach(certToken -> tslCertificateSource.addTSLCertificate(certToken.getCertificate()));
            configuration.setTSL(tslCertificateSource);
        }
    }

    @Autowired
    public void setTrustedListSource(TrustedListsCertificateSource trustedListSource) {
        this.trustedListSource = trustedListSource;
    }

}
