package com.zhang.ucenterservice.service;

import com.zhang.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.ucenterservice.entity.vo.LoginVo;
import com.zhang.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    UcenterMember login(LoginVo loginVo);

    UcenterMember getUserByPhone(String phone);

    void loginout();
}
