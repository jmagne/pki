package com.netscape.certsrv.security;

import java.security.NoSuchAlgorithmException;

import org.mozilla.jss.asn1.OBJECT_IDENTIFIER;
import org.mozilla.jss.crypto.EncryptionAlgorithm;
import org.mozilla.jss.crypto.IVParameterSpec;
import org.mozilla.jss.crypto.KeyGenAlgorithm;
import org.mozilla.jss.crypto.KeyWrapAlgorithm;
import org.mozilla.jss.crypto.SymmetricKey;
import org.mozilla.jss.crypto.SymmetricKey.Type;

public class WrappingParams {
    // session key attributes
    SymmetricKey.Type skType;
    KeyGenAlgorithm skKeyGenAlgorithm;
    int skLength;

    // wrapping algorithm for session key
    KeyWrapAlgorithm skWrapAlgorithm;

    // Encryption algorithm for payload
    EncryptionAlgorithm payloadEncryptionAlgorithm;

    //wrapping algorithm for payload
    KeyWrapAlgorithm payloadWrapAlgorithm;

    // payload encryption IV
    IVParameterSpec payloadEncryptionIV;

    // payload wrapping IV
    IVParameterSpec payloadWrappingIV;

    public WrappingParams(Type skType, KeyGenAlgorithm skKeyGenAlgorithm, int skLength,
            KeyWrapAlgorithm skWrapAlgorithm, EncryptionAlgorithm payloadEncryptionAlgorithm,
            KeyWrapAlgorithm payloadWrapAlgorithm, IVParameterSpec payloadEncryptIV, IVParameterSpec payloadWrapIV) {
        super();
        this.skType = skType;
        this.skKeyGenAlgorithm = skKeyGenAlgorithm;
        this.skLength = skLength;
        this.skWrapAlgorithm = skWrapAlgorithm;
        this.payloadEncryptionAlgorithm = payloadEncryptionAlgorithm;
        this.payloadWrapAlgorithm = payloadWrapAlgorithm;
        this.payloadEncryptionIV = payloadEncryptIV;
        this.payloadWrappingIV = payloadWrapIV;
    }

    public WrappingParams() {}

    public WrappingParams(String encryptOID, String wrapName, String priKeyAlgo, IVParameterSpec encryptIV, IVParameterSpec wrapIV)
            throws NumberFormatException, NoSuchAlgorithmException {
        EncryptionAlgorithm encrypt = EncryptionAlgorithm.fromOID(new OBJECT_IDENTIFIER(encryptOID));

        KeyWrapAlgorithm wrap = null;
        if (wrapName != null) {
            wrap = KeyWrapAlgorithm.fromString(wrapName);
            this.payloadWrapAlgorithm = wrap;
        }

        switch (encrypt.getAlg().toString()) {
        case "AES":
            this.skType = SymmetricKey.AES;
            this.skKeyGenAlgorithm = KeyGenAlgorithm.AES;
            if (wrap == null) this.payloadWrapAlgorithm = KeyWrapAlgorithm.AES_KEY_WRAP_PAD;
            break;
        case "DESede":
            this.skType = SymmetricKey.DES3;
            this.skKeyGenAlgorithm = KeyGenAlgorithm.DES3;
            this.skWrapAlgorithm = KeyWrapAlgorithm.DES3_CBC_PAD;
            if (wrap == null) this.payloadWrapAlgorithm = KeyWrapAlgorithm.DES3_CBC_PAD;
            break;
        case "DES":
            this.skType = SymmetricKey.DES;
            this.skKeyGenAlgorithm = KeyGenAlgorithm.DES;
            this.skWrapAlgorithm = KeyWrapAlgorithm.DES3_CBC_PAD;
            if (wrap == null) this.payloadWrapAlgorithm = KeyWrapAlgorithm.DES_CBC_PAD;
            break;
        default:
            throw new NoSuchAlgorithmException("Invalid algorithm");
        }

        this.skLength = encrypt.getKeyStrength();
        if (priKeyAlgo.equals("EC")) {
            this.skWrapAlgorithm = KeyWrapAlgorithm.AES_ECB;
        } else {
            this.skWrapAlgorithm = KeyWrapAlgorithm.RSA;
        }

        this.payloadEncryptionAlgorithm = encrypt;
        this.payloadEncryptionIV = encryptIV;
        this.payloadWrappingIV = wrapIV;
    }

    public SymmetricKey.Type getSkType() {
        return skType;
    }

    public void setSkType(SymmetricKey.Type skType) {
        this.skType = skType;
    }

    public void setSkType(String skTypeName) throws NoSuchAlgorithmException {
        this.skType = SymmetricKey.Type.fromName(skTypeName);
    }

    public KeyGenAlgorithm getSkKeyGenAlgorithm() {
        return skKeyGenAlgorithm;
    }

    public void setSkKeyGenAlgorithm(KeyGenAlgorithm skKeyGenAlgorithm) {
        this.skKeyGenAlgorithm = skKeyGenAlgorithm;
    }

    public void setSkKeyGenAlgorithm(String algName) throws NoSuchAlgorithmException {
        // JSS mapping is not working.  Lets just do something brain-dead to
        // handle the cases we expect.
        if (algName.equalsIgnoreCase("AES")) {
            this.skKeyGenAlgorithm = KeyGenAlgorithm.AES;
        } else if (algName.equalsIgnoreCase("DES")) {
            this.skKeyGenAlgorithm = KeyGenAlgorithm.DES;
        } else if (algName.equalsIgnoreCase("DESede")) {
            this.skKeyGenAlgorithm = KeyGenAlgorithm.DES3;
        } else if (algName.equalsIgnoreCase("DES3")) {
            this.skKeyGenAlgorithm = KeyGenAlgorithm.DES3;
        }
    }

    public int getSkLength() {
        return skLength;
    }

    public void setSkLength(int skLength) {
        this.skLength = skLength;
    }

    public KeyWrapAlgorithm getSkWrapAlgorithm() {
        return skWrapAlgorithm;
    }

    public void setSkWrapAlgorithm(KeyWrapAlgorithm skWrapAlgorithm) {
        this.skWrapAlgorithm = skWrapAlgorithm;
    }

    public void setSkWrapAlgorithm(String name) throws NoSuchAlgorithmException {
        this.skWrapAlgorithm = KeyWrapAlgorithm.fromString(name);
    }

    public EncryptionAlgorithm getPayloadEncryptionAlgorithm() {
        return payloadEncryptionAlgorithm;
    }

    public void setPayloadEncryptionAlgorithm(EncryptionAlgorithm payloadEncryptionAlgorithm) {
        this.payloadEncryptionAlgorithm = payloadEncryptionAlgorithm;
    }

    public void setPayloadEncryptionAlgorithm(String algName, String modeName, String paddingName, int keyStrength)
            throws NoSuchAlgorithmException {
        this.payloadEncryptionAlgorithm = EncryptionAlgorithm.lookup(algName, modeName, paddingName, keyStrength);
    }

    public KeyWrapAlgorithm getPayloadWrapAlgorithm() {
        return payloadWrapAlgorithm;
    }

    public void setPayloadWrapAlgorithm(KeyWrapAlgorithm payloadWrapAlgorithm) {
        this.payloadWrapAlgorithm = payloadWrapAlgorithm;
    }

    public void setPayloadWrapAlgorithm(String name) throws NoSuchAlgorithmException {
        this.payloadWrapAlgorithm = KeyWrapAlgorithm.fromString(name);
    }

    public IVParameterSpec getPayloadEncryptionIV() {
        return payloadEncryptionIV;
    }

    public void setPayloadEncryptionIV(IVParameterSpec payloadEncryptionIV) {
        this.payloadEncryptionIV = payloadEncryptionIV;
    }

    public IVParameterSpec getPayloadWrappingIV() {
        return payloadWrappingIV;
    }

    public void setPayloadWrappingIV(IVParameterSpec payloadWrappingIV) {
        this.payloadWrappingIV = payloadWrappingIV;
    }
}
