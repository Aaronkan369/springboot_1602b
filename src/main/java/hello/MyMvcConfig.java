//package hello;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import hello.core.LoginInteceptor;
//
//@Configuration
//public class MyMvcConfig extends WebMvcConfigurerAdapter {
//	//增加拦截器
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new LoginInteceptor())    //指定拦截器类
//                .addPathPatterns("/**").excludePathPatterns("/open/**","/stu/**","/static/**");        //指定该类拦截的url
//    }
//}