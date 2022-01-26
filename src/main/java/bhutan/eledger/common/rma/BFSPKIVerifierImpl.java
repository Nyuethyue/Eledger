package bhutan.eledger.common.rma;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Log4j2
@Component
class BFSPKIVerifierImpl implements BFSPKIVerifier {

    public boolean verify(BFSPKIVerifyContext context) throws SignatureException, InvalidKeyException, CertificateException, FileNotFoundException, IOException, NoSuchAlgorithmException {
        log.debug("Verifying context: {}", context);

        PublicKey publicKey = getX509Certificate(context.getPubKeyFileName()).getPublicKey();

        Signature signature = Signature.getInstance(context.getSignatureAlg());
        signature.initVerify(publicKey);
        signature.update(context.getCheckSumStr().getBytes());

        var checkSumFromMsgBytes = hexStringToByteArray(context.getCheckSumFromMsg());

        return signature.verify(checkSumFromMsgBytes);
    }

    private Certificate getX509Certificate(String pubKeyFileName) throws CertificateException, IOException {

        try (InputStream inStream = new FileInputStream(pubKeyFileName)) {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            return certFactory
                    .generateCertificate(inStream);
        }
    }

    private byte[] hexStringToByteArray(String strHex) {
        byte[] bytKey = new byte[(strHex.length() / 2)];
        int y = 0;

        for (int x = 0; x < bytKey.length; x++) {
            String strByte = strHex.substring(y, (y + 2));
            if (strByte.equals("FF")) {
                bytKey[x] = (byte) 0xFF;
            } else {
                bytKey[x] = (byte) Integer.parseInt(strByte, 16);
            }
            y = y + 2;
        }
        return bytKey;
    }
}
