package bhutan.eledger.common.rma;

import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

public interface BFSPKIVerifier {

    /**
     * @param context verify context
     *
     * @return true if valid otherwise else
     *
     * @throws SignatureException       if the passed-in signature is improperly encoded or of the wrong type, if this signature algorithm is unable to process the input data provided, etc
     * @throws InvalidKeyException      if the public key is invalid
     * @throws CertificateException     in case public key file parsing errors
     * @throws NoSuchAlgorithmException if no Provider supports a Signature implementation for the specified algorithm
     * @throws FileNotFoundException    if public key file not found
     * @throws IOException              if IO errors occurs e.g. Can't close the input stream
     */
    boolean verify(BFSPKIVerifyContext context) throws SignatureException, InvalidKeyException, CertificateException, NoSuchAlgorithmException, FileNotFoundException, IOException;

    @Data(staticConstructor = "of")
    class BFSPKIVerifyContext {
        private final String pubKeyFileName;
        private final String checkSumStr;
        private final String checkSumFromMsg;
        //SHA1withRSA
        private final String signatureAlg;
    }
}
