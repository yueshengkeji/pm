package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.listener.WebParam;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class DynamicMethodExecutorImpl implements com.yuesheng.pm.service.DynamicMethodExecutor {

    public void executeDynamicMethod(String beanName, String methodName, Object... methodArgs) {
        try {

            Object o = WebParam.webApplicationContext.getBean(beanName);
            // 加载类
            Class<?> clazz = o.getClass();
            Class[] classes;
            if (methodArgs != null) {
                classes = new Class[methodArgs.length];
                for (int i = 0; i < methodArgs.length; i++) {
                    classes[i] = methodArgs[i].getClass();
                }
            } else {
                classes = null;
            }
            // 获取方法
            Method method = findMethod(clazz, methodName, classes);
            // 调用方法
            method.invoke(o, methodArgs);

        } catch (NoSuchMethodException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeDynamicMethod(String service, FlowMessage flowMsg) {
        String[] s = service.split("\\.");
        String beanName = s[0];
        String method = s[1];
        Object o = WebParam.webApplicationContext.getBean(beanName);
        // 加载类
        Class<?> clazz = o.getClass();
        Method invoke = null;

        try {
            //查询FlowMessage类型的方法
            invoke = findMethod(clazz, method, flowMsg.getClass());
            invoke.invoke(o, flowMsg);
        } catch (NoSuchMethodException e) {
            try {
                //查找String类型参数的方法
                invoke = findMethod(clazz, method, String.class);
                invoke.invoke(o, flowMsg.getFrameId());
            } catch (NoSuchMethodException ex) {
                //print this error
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private Method findMethod(Class<?> clazz, String methodName, Class... paramTypes)
            throws NoSuchMethodException {
        if (paramTypes == null) {
            return clazz.getDeclaredMethod(methodName);
        } else {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        }
    }
}
