/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author Amila_10367
 */
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cryptography {

    private Cipher dcipher;
    private Cipher ecipher;
    private String encryptionKey;

    public Cryptography() {
        encryptionKey = "StargateFX";
        initData();
    }

    public Cryptography(String encryptionKey) {
        this.encryptionKey = "StargateFX";
        this.encryptionKey = encryptionKey.trim();
        initData();
    }

    public String decrypt(String str) {
        try {
            byte utf8[];
            byte dec[] = (new BASE64Decoder()).decodeBuffer(str);
            utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (IOException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        } catch (IllegalBlockSizeException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        } catch (BadPaddingException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        }
        return "";
    }

    public String encrypt(String str) {
        try {
            byte[] enc;
            byte[] utf8 = str.getBytes("UTF8");
            enc = ecipher.doFinal(utf8);
            return (new BASE64Encoder()).encode(enc);
        } catch (IllegalBlockSizeException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        } catch (BadPaddingException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        } catch (UnsupportedEncodingException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.encrypt() ")).append(e.getMessage()).toString());
        }
        return "";
    }

    private void initData() {
        byte salt[] = {
            -87, -101, -56, 50, 86, 52, -29, 3
        };
        int iterationCount = 19;
        try {
            java.security.spec.KeySpec keySpec = new PBEKeySpec(encryptionKey.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());
            java.security.spec.AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
            ecipher.init(1, key, paramSpec);
            dcipher.init(2, key, paramSpec);
        } catch (InvalidAlgorithmParameterException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.initData() ")).append(e.getMessage()).toString());
        } catch (InvalidKeySpecException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.initData() ")).append(e.getMessage()).toString());
        } catch (NoSuchPaddingException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.initData() ")).append(e.getMessage()).toString());
        } catch (NoSuchAlgorithmException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.initData() ")).append(e.getMessage()).toString());
        } catch (InvalidKeyException e) {
            System.out.println((new StringBuilder("ERROR: Cryptography.initData() ")).append(e.getMessage()).toString());
        }
    }

    public static void main(String args[]) {
        try {
            Cryptography cry = new Cryptography();
            System.out.println(cry.decrypt("h1nXgPOTUeY="));
            System.out.println(cry.decrypt("ZpyHjmuJBJzWZGc4mQRiFg=="));
            System.out.println("");
        } catch (Exception e) {
            System.out.println((new StringBuilder("ERROR: - ")).append(e.getMessage()).toString());
        }
    }
}
