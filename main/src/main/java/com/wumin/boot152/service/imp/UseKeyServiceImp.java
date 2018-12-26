package com.wumin.boot152.service.imp;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.wumin.boot152.common.entity.PageData;
import com.wumin.boot152.common.exception.ServiceException;
import com.wumin.boot152.common.util.DateUtils;
import com.wumin.boot152.common.util.PasswordUtils;
import com.wumin.boot152.entity.FirmFarmer;
import com.wumin.boot152.entity.FirmKey;
import com.wumin.boot152.service.UseKeyService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
@Service
public class UseKeyServiceImp implements UseKeyService {
    @Resource
    MongoTemplate mongoTemplate;
    @Override
    public PageData getInfo(String param, String id) {
        FirmFarmer firmFarmer=checkAndInitData(param,id);
        //再根据firmFarmer中的内容去进行设置查询条件
        return null;
    }

    private FirmFarmer checkAndInitData(String param, String id) {
        //根据id查mongo中对应的key
        Query q = new Query();
        org.springframework.data.mongodb.core.query.Criteria criteria = org.springframework.data.mongodb.core.query.Criteria.where("id").is(id);
        q.addCriteria(criteria);
        FirmKey firmKey = this.mongoTemplate.findOne(q, FirmKey.class);
        if (firmKey == null) {
            throw new ServiceException("非法请求,没有对应的公司Key信息!");
        }
        //对查询条件进行解码
        String jsonFirmFarmer=getJsonString(param,firmKey);
        FirmFarmer firmFarmer= JSONObject.parseObject(jsonFirmFarmer,FirmFarmer.class);
        if(firmFarmer.getQueryTime()==null){
            throw new ServiceException("查询时间不能为空（秒）!");
        }
        long time= DateUtils.diffSecond(new Date(),firmFarmer.getQueryTime());
        if(time>30000){
            throw new ServiceException("查询已超时!");
        }
        return firmFarmer;
    }
    private String getJsonString(String param, FirmKey firmKey) {
        byte[] db = new byte[256];
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, PasswordUtils.getKey(firmKey.getKey()));
            //执行操作
            byte[] result = cipher.doFinal(org.apache.shiro.codec.Base64.decode(param));
            return new String(result, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        }  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw  new ServiceException("加密算法不正确!");
        }
    }
}
