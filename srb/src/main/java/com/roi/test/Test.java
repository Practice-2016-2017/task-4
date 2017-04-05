package com.roi.test;

import com.roi.config.Config;
import com.roi.entity.Admin;
import com.roi.repository.AdminRepository;
import com.roi.repository.StudentRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx =new AnnotationConfigApplicationContext(Config.class);
        AdminRepository repo1 = ctx.getBean(AdminRepository.class);
        Admin a1=repo1.saveAndFlush(new Admin(100002,"root"));

        for(Admin a: repo1.findAll()) {
         System.out.println(a.getId());
         System.out.println(a.getLogin());
         System.out.println(a.getPassword());
        }
        ctx.close();
    }

}
