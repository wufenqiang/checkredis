package com.weather.bigdata.it.app.checkredis;

import com.weather.bigdata.it.app.checkredis.provider.ICacheProvider;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckredisApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPush() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String nowdate = df.format(new Date());
        String key = "tqsign";
        String md5 = getMD5(key + nowdate);
        System.out.println(md5);


        //get
        String url = "http://wechat.weatherdt.com/ReceiveMessages/SaveReceiveMessages?sign=" + md5
                + "&name=test&message=这是个测试"
                + "&contacts=别文祥";


        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("sign", md5);
        hashMap.put("name", "天气网页面更新情况");
        hashMap.put("message", "更新成功，查询界面数目为3223");
        hashMap.put("contacts", "别文祥");
        hashMap.put("pushtype", "0");
        //post
//        HttpUtil.postParams("http://wechat.weatherdt.com/ReceiveMessages/SaveReceiveMessages", hashMap);

    }

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digestResult = md.digest(str.getBytes(Charset.forName("UTF-8")));

            return Hex.encodeHexString(digestResult).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Autowired
    ICacheProvider jedisCacheProvider;

    @Test
    public void testRedisUtil(){
        System.out.println(jedisCacheProvider.getString("n1h_243_4260"));
    }
}
