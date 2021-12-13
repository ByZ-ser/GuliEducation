package com.zhang.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zhang.oss.service.OssService;
import com.zhang.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        try {
            //创建OSS对象
            OSS build = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            //得到用户上传的文件输入流
            InputStream inputStream = file.getInputStream();
            //得到文件名
            String originalFilename = file.getOriginalFilename();
            //在文件名之后添加一个唯一值，使得每个文件名都唯一
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            originalFilename=uuid+originalFilename;


            //对所有文件按照日期进行分类
            String date = new DateTime().toString("yyyy/MM/dd");
            originalFilename=date+"/"+originalFilename;

            //上传文件到oss
            build.putObject(bucketName,originalFilename,inputStream);
            //关闭oss
            build.shutdown();
            //返回上传文件的url
            String url = "https://"+bucketName+"."+endPoint+"/"+originalFilename;
            return url;
        }catch (Exception e){
            return null;
        }

    }
}
