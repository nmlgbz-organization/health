package cn.whj.Log;

import cn.whj.pojo.SysLog;
import cn.whj.service.SysLogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAop {

    @Autowired
    private HttpServletRequest request;
    @Reference
    private SysLogService sysLogService;


    private Date startTime; // 访问时间
    private Class executionClass;// 访问的类
    private Method executionMethod; // 访问的方法


    @AfterThrowing("execution(* cn.whj.controller.*.*(..))")
    public void doAfterThrowing(JoinPoint jp) throws Exception {

        startTime = new Date(); // 访问时间
        // 获取访问的类
        executionClass = jp.getTarget().getClass();
        // 获取访问的方法
        String methodName = jp.getSignature().getName();// 获取访问的方法的名称

        Object[] args = jp.getArgs();// 获取访问的方法的参数
        if (args == null || args.length == 0) {// 无参数
            executionMethod = executionClass.getMethod(methodName);
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(methodName, classArgs);

        }
        System.out.println(executionClass+"asd"+executionMethod);
        RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
        if (classAnnotation != null) {

            // 获取方法上的@RequestMapping对象
            RequestMapping methodAnnotation = (RequestMapping)executionMethod.getAnnotation(RequestMapping.class);

            if (methodAnnotation != null) {

                String url = ""; // 它的值应该是类上的@RequestMapping的value+方法上的 @RequestMapping的value

                url = classAnnotation.value()[0] + methodAnnotation.value()[0];

                SysLog sysLog = new SysLog();
                // 获取访问时长
                Long executionTime = new Date().getTime() - startTime.getTime();
                // 将sysLog对象属性封装

                sysLog.setUrl(url);
                // 获取ip
                String ip = request.getRemoteAddr();
                sysLog.setIp(ip);

                // 可以通过securityContext获取，也可以从request.getSession中获取
                SecurityContext context = SecurityContextHolder.getContext(); //
                request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                String username = ((User) (context.getAuthentication().getPrincipal())).getUsername();
                sysLog.setUsername(username);

                sysLog.setMethod("[类名]" + executionClass.getName() + "[方法名]" + executionMethod.getName());
                sysLog.setVisitTime(startTime);

                sysLogService.save(sysLog);
            }


        }

    }









}