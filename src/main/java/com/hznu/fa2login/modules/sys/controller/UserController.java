package com.hznu.fa2login.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hznu.fa2login.common.utils.GoogleAuthenticator;
import com.hznu.fa2login.common.validator.Assert;
import com.hznu.fa2login.common.validator.group.UpdateGroup;
import com.hznu.fa2login.modules.sys.beans.ForgetForm;
import com.hznu.fa2login.modules.sys.entity.Ccode;
import com.hznu.fa2login.modules.sys.entity.User;
import com.hznu.fa2login.modules.sys.service.CcodeService;
import com.hznu.fa2login.modules.sys.service.UserService;
import com.hznu.fa2login.modules.sys.service.encryption.PasswordHelper;
import com.hznu.fa2login.myBeans.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 23:08
 * @param:
 * @return:
 */
@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private CcodeService ccodeService;


   @PostMapping("/add")
    public R addOne(@RequestBody User user){
       if(user.getUsername()==null){
           return R.error("用户名不能为空");
       }
       if(user.getPassword()==null){
           return R.error("密码不能为空");
       }
       if(user.getEmail()==null){
           return R.error("邮箱不能为空");
       }
       if(user.getMobilephone()==null){
           return R.error("手机不能为空");
       }
       Assert.isNotNull(userService.selectOne(new EntityWrapper<User>().eq("username",user.getUsername())),"用户名已经存在");

       passwordHelper.encryptPassword(user);
       String key=GoogleAuthenticator.genSecret();
       user.setKey(key);
//       GoogleAuthenticator.getQRBarcodeURL(user.getUsername(),"localhost",key);
       userService.insert(user);
       return R.ok("用户注册成功").put("key",key).put("url",GoogleAuthenticator.getQRBarcode(user.getUsername(),"localhost",key));
   }
   @PostMapping("/forget")
   public R forget(@RequestBody ForgetForm forgetForm){
       try{
           if(!forgetForm.getPassword().equals(forgetForm.getNewpassword())){
               return R.error("两次密码输入不一致");
           }else{
               User user=userService.selectOne(new EntityWrapper<User>().eq("username",forgetForm.getUsername()));
               if(user==null){
                   return R.error("用户不存在");
               }else{
                   Ccode ccode=ccodeService.selectOne(new EntityWrapper<Ccode>().eq("user_id",user.getId()));
                   if(ccode==null){
                       return R.error("验证码错误");
                   }else if(ccode.getExptime().getTime()<new Date().getTime()){
                       return R.error("验证码已经过期，请重新获取");
                   }else{
                       if(ccode.getCacode().equals(forgetForm.getCode())){
                           User user1=new User();
                           user1.setId(user.getId());
                           user1.setPassword(forgetForm.getPassword());
                           passwordHelper.encryptPassword(user1);
                           userService.update(user1, new EntityWrapper<User>().eq("id",user.getId()));
                           return R.ok("修改成功");
                       }else{
                           return R.error("验证码错误");
                       }
                   }
               }
           }

       }catch(Exception e){
       return R.error();
       }
   }

   @PostMapping("/sendEmail")
    public R sendEmail(@RequestParam String username){
       String mailCode="";
       String email="";
       Assert.isNull(username,"用户名为空");
       try{
           User user=userService.selectOne(new EntityWrapper<User>().eq("username",username));
           if(user==null){
               return R.error("用户不存在");
           }else if(user.getEmail()==null){
               return R.error("用户邮箱不存在");
           }else{
               mailCode=getSmsCode();
               JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
               mailSender.setHost("smtp.sina.cn");
               mailSender.setUsername("digitu@sina.cn");
               mailSender.setPassword("hznu180109");
               email=user.getEmail();
               mailSender.setDefaultEncoding("UTF-8");
               MimeMessage mimeMessage = mailSender.createMimeMessage();
               MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
               mimeMessageHelper.setText("<font color='red'>您的验证码为"+mailCode+",此验证码仅用于测试系统,5分钟内有效。如非本人操作请忽略。</font>",true);
               mimeMessageHelper.setFrom(mailSender.getUsername());
               mimeMessageHelper.setTo(email);
               mimeMessageHelper.setSubject("2FA认证系统邮箱验证");
               mailSender.send(mimeMessage);
               Date extime=new Date(new Date().getTime()+5*60*1000);
               Ccode ccode=new Ccode();
               ccode.setUserId(user.getId());
               ccode.setExptime(extime);
               ccode.setCacode(mailCode);
              if(ccodeService.selectOne(new EntityWrapper<Ccode>().eq("user_id",user.getId()))!=null){
                   ccodeService.update(ccode,new EntityWrapper<Ccode>().eq("user_id",user.getId()));
                   return R.ok("发送成功");
              }else{
                  ccodeService.insert(ccode);
                  return R.ok("发送成功");
              }
           }
       }catch(Exception e){
           e.printStackTrace();
       return R.error();
       }
   }


    public String getSmsCode() {
        Random random = new Random();
        int codeLength = 6;
        String code = "";
        int n;
        while (true) {
            n = random.nextInt(10);
//                if(code.contains(String.valueOf(n))) //控制验证码不出现重复数字
//                    continue;
            code += n;
            if (code.length() == codeLength) //验证码6位
                break;
        }
        return code;
    }
}
