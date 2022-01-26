package bhutan.eledger.common.rma;

import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface BFSPKISigner {

    /**
     * @param context sign context
     *
     * @return Signed data
     *
     * @throws NoSuchAlgorithmException when specified algorithm is not available.
     * @throws SignatureException       when this signature algorithm is unable to process the input data provided
     * @throws InvalidKeyException      when private key is invalid.
     * @throws FileNotFoundException    when private key file not found.
     * @throws IOException              when there is an error during parse private key to key-pair or in case of can't IO error like stream closing.
     */
    String sign(BFSPKISignContext context) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, FileNotFoundException, IOException;

    @Data(staticConstructor = "of")
    class BFSPKISignContext {
        private final String pvtKeyFileName;
        private final String dataToSign;
        private final String signatureAlg;
    }
}
