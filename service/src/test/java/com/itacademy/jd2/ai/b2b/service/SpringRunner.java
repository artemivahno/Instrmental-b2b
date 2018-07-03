package com.itacademy.jd2.ai.b2b.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itacademy.jd2.ai.b2b.service.IBrandService;

public class SpringRunner {

    public static void main(final String[] args) {

        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "service-context.xml");
        System.out.println(context);

        final String[] beanDefinitionNames = context.getBeanDefinitionNames();

        System.out.println("Beans in context:");
        for (final String beanName : beanDefinitionNames) {
            System.out.println(beanName);
        }
        final IBrandService bean = context.getBean(IBrandService.class);
        System.out.println(bean);
    }

}