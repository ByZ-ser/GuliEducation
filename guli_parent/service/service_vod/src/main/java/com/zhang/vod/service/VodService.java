package com.zhang.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String upload(MultipartFile file);

    void deleteVideo(String videoId);

    void deleteVideos(List videoList);

    String getAuth(String videoId);
}
