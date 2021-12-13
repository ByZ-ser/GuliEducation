package com.zhang.educms.service;

import com.zhang.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-09
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAll();
}
