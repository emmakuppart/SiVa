package ee.openeid.tsl.configuration;

import ee.openeid.tsl.keystore.DSSKeyStoreFactoryBean;
import eu.europa.esig.dss.client.http.commons.CommonsDataLoader;
import eu.europa.esig.dss.tsl.TrustedListsCertificateSource;
import eu.europa.esig.dss.validation.CommonCertificateVerifier;
import eu.europa.esig.dss.x509.KeyStoreCertificateSource;
import eu.europa.esig.dss.x509.crl.CRLSource;
import eu.europa.esig.dss.x509.ocsp.OCSPSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(TSLLoaderConfigurationProperties.class)
public class TSLLoaderConfiguration {

    @Value("${keystore.type}")
    String keystoreType;

    @Value("${keystore.filename}")
    String keystoreFilename;

    @Value("${keystore.password}")
    String keystorePassword;

    @Bean
    public DSSKeyStoreFactoryBean dssKeyStore() {
        DSSKeyStoreFactoryBean dssKeyStoreFactoryBean = new DSSKeyStoreFactoryBean();
        dssKeyStoreFactoryBean.setKeyStoreType(keystoreType);
        dssKeyStoreFactoryBean.setKeyStoreFilename(keystoreFilename);
        dssKeyStoreFactoryBean.setKeyStorePassword(keystorePassword);
        return dssKeyStoreFactoryBean;
    }

    @Bean
    public CRLSource crlSource() {
        return new AlwaysFailingCRLSource();
    }

    @Bean
    public OCSPSource ocspSource() {
        return new AlwaysFailingOCSPSource();
    }


    @Bean
    public CommonCertificateVerifier certificateVerifier(TrustedListsCertificateSource trustedListSource, OCSPSource ocspSource, CRLSource crlSource) {
        return new CommonCertificateVerifier(trustedListSource, crlSource, ocspSource, new CommonsDataLoader());
    }

    @Bean
    public TrustedListsCertificateSource trustedListSource(KeyStoreCertificateSource keyStoreCertificateSource) {
        TrustedListsCertificateSource trustedListsCertificateSource = new TrustedListsCertificateSource();
        trustedListsCertificateSource.setDssKeyStore(keyStoreCertificateSource);
        return trustedListsCertificateSource;
    }


}
