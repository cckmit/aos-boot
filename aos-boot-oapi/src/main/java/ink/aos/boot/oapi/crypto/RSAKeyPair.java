package ink.aos.boot.oapi.crypto;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public final class RSAKeyPair {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSAKeyPair(KeyPair keyPair) {
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getPrivateKeyStr() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public String getPublicKeyStr() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}
