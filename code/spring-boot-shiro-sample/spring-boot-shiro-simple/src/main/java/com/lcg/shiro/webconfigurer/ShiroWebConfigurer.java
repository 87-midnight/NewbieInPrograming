package com.lcg.shiro.webconfigurer;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

import javax.servlet.Filter;

/**
 * @author linchuangang
 * @createTime 2020/11/4
 **/
@Configuration
public class ShiroWebConfigurer implements InitializingBean,EnvironmentAware {

    @Bean
    public Realm defineRealm(){
        return new UserAuthorityRealm();
    }

    private Environment environment;

    private RedisProperties redisProperties;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisProperties = Binder.get(environment).bind("spring.redis",RedisProperties.class).get();
    }

    @Bean
    public Cookie cookie() {
        Cookie cookie=new SimpleCookie("MY-SESSION");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager(); // crazycake 实现
        redisManager.setHost(redisProperties.getHost()+":"+redisProperties.getPort());
//        redisManager.setPassword(redisProperties.getPassword());
        redisManager.setTimeout((int) redisProperties.getTimeout().get(ChronoUnit.SECONDS) * 1000);
        return redisManager;
    }
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(720000); // 设置session超时
        sessionManager.setDeleteInvalidSessions(true); // 删除无效session
        sessionManager.setSessionIdCookie(cookie()); // 设置JSESSIONID
        sessionManager.setSessionDAO(sessionDAO()); // 设置sessionDAO
        return sessionManager;
    }
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager(); // crazycake 实现
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO(); // crazycake 实现
        sessionDAO.setExpire(72000);
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator()); // Session ID 生成器
        return sessionDAO;
    }

    /**
     * 4. 配置LifecycleBeanPostProcessor，可以来自动的调用配置在Spring IOC容器中 Shiro Bean 的生命周期方法
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 5. 启用IOC容器中使用Shiro的注解，但是必须配置第四步才可以使用
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(defineRealm());
        manager.setSessionManager(sessionManager());
        manager.setCacheManager(redisCacheManager());
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        /**
         *  Shiro连接约束配置,即过滤链的定义
         *      anon：它对应的过滤器里面是空的,什么都没做
         *      authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         *     roles：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
         *     perms：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
         *     rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
         *     port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
         *     authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
         *     ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
         *     user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
         */
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        Map<String, Filter> filters = new LinkedHashMap<>();
        //自定义表单、ajax提交拦截过滤器
        filters.put("authc",new AjaxFormAuthenticationFilter());
        bean.setFilters(filters);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorizedurl");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/unauth/login", "anon");
        map.put("/**", "authc");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }


}
