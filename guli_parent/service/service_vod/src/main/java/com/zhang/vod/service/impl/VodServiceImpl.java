package com.zhang.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zhang.servicebase.exceptionhandler.GuliException;
import com.zhang.vod.service.VodService;
import com.zhang.vod.utils.ConstantPropertiesUtil;
import com.zhang.vod.utils.InitObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VodServiceImpl implements VodService {



    @Override
    public String upload(MultipartFile file) {

        try {
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String fileName=file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream=file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
               // log.warn(errorMessage);
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }
            return videoId;
        }catch (Exception e){
            throw new GuliException(20001, "guli vod 服务上传失败");
        }

    }


    //删除阿里云端的视频
    @Override
    public void deleteVideo(String videoId) {
        try {
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest videoRequest = new DeleteVideoRequest();
            videoRequest.setVideoIds(videoId);

            client.getAcsResponse(videoRequest);

        }catch (Exception e){
                throw new GuliException(20001,"删除视频失败");
        }
    }

    @Override
    public void deleteVideos(List videoList) {
        try {
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoRequest videoRequest = new DeleteVideoRequest();
            String join = org.apache.commons.lang.StringUtils.join(videoList.toArray(), ",");
            videoRequest.setVideoIds(join);

            client.getAcsResponse(videoRequest);

        }catch (Exception e){
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 获取视频凭证
     * @param videoId
     * @return
     */
    @Override
    public String getAuth(String videoId) {
        try {
            //根据视频id获取视频凭证
            DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5t6FFbG7xK44BMpnmrhW","703wxTIApe2Bjf88rukjxMJw0iH4cc");
            GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse authResponse = new GetVideoPlayAuthResponse();
            authRequest.setVideoId(videoId);
            authResponse = defaultAcsClient.getAcsResponse(authRequest);
           String auth=authResponse.getPlayAuth();
            return auth;
        }catch (Exception e){
            throw new GuliException(20001,"获取凭证失败");
        }
    }
}
