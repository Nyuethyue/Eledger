package bhutan.eledger.common.rma;

import lombok.extern.log4j.Log4j2;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;

@Log4j2
@Component
class BFSPKISignerImpl implements BFSPKISigner {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    @Override
    public String sign(BFSPKISignContext context) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        log.debug("Signing context: {}", context);

        PrivateKey privateKey = getPrivateKey(context.getPvtKeyFileName());

        var provider = Security.getProvider("BC");

        Signature signature = Signature.getInstance(context.getSignatureAlg(), provider);
        signature.initSign(privateKey);

        signature.update(context.getDataToSign().getBytes());
        byte[] signatureBytes = signature.sign();

        return byteArrayToHexString(signatureBytes);

    }

    private static PrivateKey getPrivateKey(String pvtKeyFileName) throws FileNotFoundException, IOException {

        try (FileReader pvtFileReader = new FileReader(pvtKeyFileName); PEMParser pvtPemParser = new PEMParser(pvtFileReader)) {
            KeyPair keyPair = (KeyPair) pvtPemParser.readObject();

            return keyPair.getPrivate();
        }
    }

    public String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (byte aByte : bytes) {
            sb.append(HEX_CHAR[(aByte & 0xf0) >>> 4]);
            sb.append(HEX_CHAR[aByte & 0x0f]);
        }
        return sb.toString();
    }
}
