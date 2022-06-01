package com.ctvv.util;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordHashingUtil
{
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SALT_BYTES = 16;
    private static final int HASH_BYTES = 16;
    private static final int PBKDF2_ITERATIONS = 10000;
    private static final int SALT_INDEX = 0;
    private static final int PBKDF2_INDEX = 1;

    public static String createHash(String password)

    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        byte[] hash = PBKDF2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTES);
        return FromByteArrayToHex(salt) + ":" +  FromByteArrayToHex(hash);
    }


    public static boolean validatePassword(String password, String rightPassword)
    {
        String[] params = rightPassword.split(":");
        byte[] salt = fromHexToByteArray(params[SALT_INDEX]);
        byte[] hash = fromHexToByteArray(params[PBKDF2_INDEX]);

        byte[] testHash = PBKDF2(password.toCharArray(), salt, PBKDF2_ITERATIONS, hash.length);
        return compareTwoHashes(hash, testHash);
    }
    private static boolean compareTwoHashes(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
    private static byte[] PBKDF2(char[] password, byte[] salt, int iterations, int bytes)
    {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    private static byte[] fromHexToByteArray(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }
    private static String FromByteArrayToHex(byte[] array)
    {
        BigInteger big= new BigInteger(1, array);
        String hex = big.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}