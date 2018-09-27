import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.wumin.boot152.common.util.DateUtils;
import com.wumin.boot152.common.util.HttpRequestUtil;
import com.wumin.boot152.common.util.PasswordUtils;
import com.wumin.boot152.entity.FirmFarmer;

import javax.crypto.Cipher;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirmFarmerDemo {
    private static FirmFarmer initFirmFarmer(){
        FirmFarmer firmFarmer=new FirmFarmer();
        firmFarmer.setPage(1);
        firmFarmer.setPageSize(10);
//        firmFarmer.setMobile("131313131");
//        firmFarmer.setTaxCode("111111");
//        firmFarmer.setBankCard("1213123123121");
//        firmFarmer.setIdentityCode("32323412353463456");
//        firmFarmer.setName("耿声传");
        firmFarmer.setBeginTime(DateUtils.parseDate("2018-05-15 00:00:00"));
        firmFarmer.setEndTime(DateUtils.parseDate("2018-05-16 00:00:00"));
        firmFarmer.setQueryTime(new Date());
        return firmFarmer;
    }

    /**
     * 加密查询条件
     * @param firmFarmerJson 查询条件json串
     * @param key    密钥
     * @return
     */
    private static  String initParams(String firmFarmerJson,String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器

            byte[] byteContent = firmFarmerJson.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, PasswordUtils.getKey(key));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64.encode(result);//通过Base64转码返回
        } catch (Exception ex) {

        }

        return null;

    }
    public static void main(String[] args) {
        FirmFarmer firmFarmer = initFirmFarmer();//查询条件
        String firmFarmerJson = JSONObject.toJSONString(firmFarmer);//查询条件json串
        //key:f539ecc30a36eb0339d88281b63c6617
        String params = initParams(firmFarmerJson, "16fdd1ddc6eae21f5c24dfe0430adcec");//查询条件加密
        String myId = "5ba1fbdd6751a62be4930319";//id
        Map<String, String> map = new HashMap<>();
        map.put("param", params);
        map.put("myId", myId);
       String farmers= HttpRequestUtil.postMap("http://localhost:9090/key/getInfo/query", map);
       System.out.println(farmers);
    }
}
