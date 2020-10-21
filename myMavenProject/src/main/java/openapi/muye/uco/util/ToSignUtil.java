package openapi.muye.uco.util;

import com.alibaba.fastjson.*;
import java.util.*;
import java.util.stream.*;
import com.google.common.base.*;
import java.net.*;
import javax.crypto.spec.*;
import javax.crypto.*;
//import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.*;
import openapi.muye.uco.config.ConfigValue;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class ToSignUtil {
//    public static String getUrl(HttpServletRequest request, final JSONObject json, final String ak, final String aks) throws Exception {
//
//        json.put("AccessKeyId", (Object)ak);
//        final List<String> params = (List<String>)json.keySet().stream().map(key -> key + "=" + encode(json.getString(key))).collect(Collectors.toList());
//        final List<String> pList = params.stream().map(ToSignUtil::encode).sorted().collect(Collectors.toList());
//        final String canonicalizedQueryString = Joiner.on("%26").join((Iterable)pList);
//        System.out.println(canonicalizedQueryString);
//        final String stringToSign = Joiner.on("&").join((Object)request.getMethod(), (Object)URLEncoder.encode("/", "utf-8"), new Object[] { canonicalizedQueryString });
//        System.out.println(stringToSign);
//        final String hmac = HmacSHA1Encrypt(stringToSign, aks + "&");
//        System.out.println(hmac);
//        final String url = Joiner.on("&").join((Iterable)params) + "&Signature=" + encode(hmac);
//        System.out.println(url);
//        return url;
//    }
    public static String getUrlNew(String request, final JSONObject json) throws Exception {
        String ak = ConfigValue.ak;
        String aks = ConfigValue.aks;
        json.put("AccessKeyId", (Object)ak);
        final List<String> params = (List<String>)json.keySet().stream().map(key -> key + "=" + encode(json.getString(key))).collect(Collectors.toList());
        final List<String> pList = params.stream().map(ToSignUtil::encode).sorted().collect(Collectors.toList());
        final String canonicalizedQueryString = Joiner.on("%26").join((Iterable)pList);
//        System.out.println(canonicalizedQueryString);
        final String stringToSign = Joiner.on("&").join((Object)request, (Object)URLEncoder.encode("/", "utf-8"), new Object[] {canonicalizedQueryString });
//        System.out.println(stringToSign);
        final String hmac = HmacSHA1Encrypt(stringToSign, aks + "&");
//        System.out.println(hmac);
        final String url = Joiner.on("&").join((Iterable)params) + "&Signature=" + encode(hmac);
        System.out.println( encode(hmac));
//        System.out.println(url);
        return url;
    }
    private static String HmacSHA1Encrypt(final String encryptText, final String encryptKey) throws Exception {
        final SecretKey secretKey = new SecretKeySpec(encryptKey.getBytes(), "HmacSHA1");
        final Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        final byte[] rawHmac = mac.doFinal(encryptText.getBytes());
        return new String(Base64.encodeBase64(rawHmac));
    }

    private static String encode(final String s) {
        final List<String> list = Lists.newArrayList();
        final String[] split;
        final String[] v = split = s.split("");
        for (final String a : split) {
            try {
                if (a.equals(" ")) {
                    list.add("%20");
                }
                else if (a.equals("*")) {
                    list.add("%2A");
                }
                else if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.~".contains(a)) {
                    list.add(URLEncoder.encode(a, "UTF-8"));
                }
                else {
                    list.add(a);
                }
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return Joiner.on("").join((Iterable)list);
    }

    public static void main(final String[] args) {
//        HttpServletRequest request = "";
//        request.setAttribute("method","POST");
        String request = "POST";
        String ak = "R2REKEyPPJCHRGp1";
        String aks = "6vbwobLfDDawbHWdPfdnPsdHSzAGRq";
        final JSONObject json = new JSONObject();
        json.put("Action","CreateAccount");
        json.put("DBInstanceId","mysql-xdnwciopnsd");
        json.put("Engine","mysql");
        json.put("RegionId","cn-tianjin-yfb");

//        System.out.println(encode(""));
        try {
            System.out.println(getUrlNew(request, json));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
