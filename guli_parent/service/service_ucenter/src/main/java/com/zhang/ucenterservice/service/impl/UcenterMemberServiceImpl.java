package com.zhang.ucenterservice.service.impl;

import com.alibaba.nacos.client.config.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.servicebase.exceptionhandler.GuliException;
import com.zhang.ucenterservice.entity.UcenterMember;
import com.zhang.ucenterservice.entity.vo.LoginVo;
import com.zhang.ucenterservice.entity.vo.RegisterVo;
import com.zhang.ucenterservice.mapper.UcenterMemberMapper;
import com.zhang.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        UcenterMember ucenterMember = new UcenterMember();
        String password=registerVo.getPassword();
        String mobile=registerVo.getMobile();
        String nickname=registerVo.getNickname();
        if(StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"注册信息为空");
        }
        //验证码校验
        String rediscode = ((String) redisTemplate.opsForValue().get(mobile));
        if(!registerVo.getCode().equals(rediscode)){
            throw new GuliException(20001,"验证码错误");
        }
        //验证手机号已经被注册
        UcenterMember member = this.getOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(member!=null){
            throw new GuliException(20001,"该账号已被注册！");
        }

        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(password);
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(ucenterMember);
    }

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @Override
    public UcenterMember login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        UcenterMember member = this.getOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(member==null){
            return null;
        }
        if(!member.getPassword().equals(loginVo.getPassword())){
           return null;
        }
        redisTemplate.opsForValue().set("usermobile",member.getMobile());
        return member;
    }

    @Override
    public UcenterMember getUserByPhone(String phone) {
        UcenterMember member = this.getOne(new QueryWrapper<UcenterMember>().eq("mobile", phone));
        if(member!=null) {
            return null;
        }
        return member;
    }

    //退出登录
    @Override
    public void loginout() {
        redisTemplate.delete("usermobile");
    }



}
