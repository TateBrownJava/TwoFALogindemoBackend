package com.hznu.fa2login.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hznu.fa2login.modules.sys.dao.CcodeDao;
import com.hznu.fa2login.modules.sys.entity.Ccode;
import com.hznu.fa2login.modules.sys.service.CcodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: TateBrown
 * @date: 2018/11/28 19:28
 * @param:
 * @return:
 */
@Transactional(rollbackFor = {Exception.class})
@Service("ccodeService")
public class CcodeServiceImpl extends ServiceImpl<CcodeDao,Ccode> implements CcodeService {
}
