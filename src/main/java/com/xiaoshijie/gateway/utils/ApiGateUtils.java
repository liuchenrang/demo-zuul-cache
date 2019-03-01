package com.xiaoshijie.gateway.utils;

import com.xiaoshijie.gateway.http.entity.ApiGate;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author chen
 */
public class ApiGateUtils {
    public static String getGateHashId(ApiGate apiGate){
        return hash(concatGateInfo(apiGate));
    }
    public static String concatGateInfo(ApiGate apiGate){
        StringBuilder builder = new StringBuilder();
        return builder.append(apiGate.getType())
                .append(apiGate.getAppid())
                .append(apiGate.getVersion())
                .append(apiGate.getFormat())
                .append(apiGate.getMethod())
                .append(apiGate.getParams()).toString();
    }
    public static   String hash(String content){
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            bytes = messageDigest.digest(bytes);
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                if (v < 16){
                    hex.append("0");
                }
                hex.append(Integer.toString(v, 16));
            }
            hex.append("-");
            hex.append(Integer.toString(content.length(), 16));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hex.toString().toUpperCase();

    }
}
