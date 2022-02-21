package bhutan.eledger.common.rma;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface BFSPKISigner {

    /**
     * @param dataToSign data to sign
     *
     * @return Signed data
     *
     * @throws NoSuchAlgorithmException when specified algorithm is not available.
     * @throws SignatureException       when this signature algorithm is unable to process the input data provided
     * @throws InvalidKeyException      when private key is invalid.
     * @throws FileNotFoundException    when private key file not found.
     * @throws IOException              when there is an error during parse private key to key-pair or in case of I/O error like can't close stream.
     */
    String sign(String dataToSign);
}
