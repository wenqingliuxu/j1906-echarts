package com.qf.j1906;

import com.qf.j1906.po.User;
import com.qf.j1906.service.impl.ReadXLS;
import com.qf.j1906.service.impl.UserServiceImpl;
import com.qf.j1906.service.impl.WriterXLS;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class J1906EchartsApplicationTests {

//    榛子云短信验证
    @Test
    public void zzduanXinYanZhengTest() {
        String apiUrl="http://sms_developer.zhenzikj.com";
        String appId="\n"+"103660";
        String appSecret="73a5348d-3b1a-47e2-b7d7-0c1930cf13ea\n";
        try {
            String smsCode=String.valueOf(new Random().nextInt(999999));
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            String tel="18229011486";
            String result=client.send(tel,"您的验证码为："+smsCode+"感谢");
            log.info("短信发送的结果："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    随机生成6位数字
    @Test
    public void codeTest(){
        String smsCode=String.valueOf(new Random().nextInt(999999));
        log.info(smsCode);
    }
    @Autowired
    private WriterXLS writerXLS;
    @Autowired
    private ReadXLS readXLS;
    @Autowired
    private UserServiceImpl userService;
    //导出Excel测试
    @Test
    public void writerExclTest(){
        List<User> userList = userService.loadAll();
        System.out.println (userList);
        writerXLS.exportExcel(userList);
    }
    //导入Excel测试
    @Test
    public void readExclTest(){
        List<User> userList = readXLS.importXLS ();
        for(User user:userList){
            System.out.println(user);
            userService.saveOneUser(user);
        }
    }


}
