package com.zhang.eduservice.client;

import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter",fallback = com.zhang.eduservice.client.impl.GetMemberInfoClient.class)
@Component
public interface GetMemberInfoClient {

    //根据手机号查询用户2
    @GetMapping("/ucenterservice/ucenter-member/findUser2/{phone}")
    public UcenterMember getUser2(@PathVariable("phone") String phone);
}
