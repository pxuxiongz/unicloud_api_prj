import org.apache.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {

    private String a = "";
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }



    public static void main(String[] args) {
        String accessKey = "amDxMhSz6hUGorLK";
        String secretKey = "6DneQhipsfe76KSxrded3oG6lqpNny";
        String bucketName = "xxx";
        String  signature = "";
        String objectName = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = dateFormat.format(new Date());
            System.out.println(date);
//            logger.info("Date- > {}",date);
            String key = UriEncoder.encode(objectName)
                    .replace("[","%5B").replace("]","%5D");
            String canonicalizedResource = "/" + bucketName + "/" + key;
//            logger.info("canonicalizedResource -> {}",canonicalizedResource);
            String data = HttpMethod.DELETE + "\n"
                    + "\n"
                    + "\n"
                    + date + "\n"
                    + canonicalizedResource;
            SecretKeySpec signinkey = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signinkey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
             signature = new String(Base64.getEncoder().encode(rawHmac));
//            logger.info("signature -> {}",signature);
        }catch (Exception e){
            e.printStackTrace();
//            return "请求失败";
        }
        String authorization = "AWS " + accessKey + ":" + signature;
        System.out.print(authorization);
//        logger.info("Authorization -> {}",authorization);
//        Map<String, Object> map = new HashMap<>();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", authorization);

    }

}
