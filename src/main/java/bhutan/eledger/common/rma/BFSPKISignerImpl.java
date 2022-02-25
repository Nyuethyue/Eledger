package bhutan.eledger.common.rma;

import bhutan.eledger.configuration.epayment.rma.RmaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HexFormat;

@Log4j2
@Component
@RequiredArgsConstructor
class BFSPKISignerImpl implements BFSPKISigner {

    private final RmaProperties rmaProperties;
    private KeyStore keyStore;

    @PostConstruct
    private void init() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        try (InputStream ksInputStream = rmaProperties.getSign().getKsResource().getInputStream()) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(ksInputStream, rmaProperties.getSign().getKsPassword().toCharArray());
        } catch (Exception e) {

        }
    }

    @Override
    public String sign(String dataToSign) {
        log.debug("Data to sign: {}", dataToSign);

        try {
            PrivateKey privateKey = getPrivateKey();

            Signature signature = Signature.getInstance(rmaProperties.getSign().getAlgorithm());

            signature.initSign(privateKey);

            signature.update(dataToSign.getBytes());
            byte[] signatureBytes = signature.sign();

            return HexFormat.of().withUpperCase().formatHex(signatureBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | UnrecoverableKeyException | KeyStoreException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    private PrivateKey getPrivateKey() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return (PrivateKey) keyStore.getKey(rmaProperties.getSign().getKeyAlias(), rmaProperties.getSign().getKeyPassword().toCharArray());
    }
}
