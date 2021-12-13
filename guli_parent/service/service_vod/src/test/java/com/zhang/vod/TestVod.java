package com.zhang.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        String accessKeyId="LTAI5t6FFbG7xK44BMpnmrhW";
        String accessKeySecret="703wxTIApe2Bjf88rukjxMJw0iH4cc";

        String title="testupload";
        String fileName="D:\\webflux\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void getVideoUrl() throws ClientException{
        //根据视频id获取视频地址
        //创建初始化对象
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5t6FFbG7xK44BMpnmrhW","703wxTIApe2Bjf88rukjxMJw0iH4cc");
        //创建获取视频地址request和respond
        GetPlayInfoRequest infoRequest = new GetPlayInfoRequest();
        GetPlayInfoResponse infoResponse = new GetPlayInfoResponse();
        //向response对象设置视频id
        infoRequest.setVideoId("a682b6761b1e4af2a0eabe1253f074a9");

        GetPlayInfoResponse acsResponse = defaultAcsClient.getAcsResponse(infoRequest);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = acsResponse.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("播放地址："+playInfo.getPlayURL()+"\n");
        }

        System.out.println("videos title:"+acsResponse.getVideoBase().getTitle()+"\n");
    }


    public static void getAuth() throws ClientException {
        //根据视频id获取视频凭证
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5t6FFbG7xK44BMpnmrhW","703wxTIApe2Bjf88rukjxMJw0iH4cc");
        GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse authResponse = new GetVideoPlayAuthResponse();
        authRequest.setVideoId("a682b6761b1e4af2a0eabe1253f074a9");

        authResponse = defaultAcsClient.getAcsResponse(authRequest);

        System.out.println("auth:"+authResponse.getPlayAuth());
    }
}
