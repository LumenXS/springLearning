package com.yc.springframework.context;

import com.yc.MyAppconfig;
import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.*;

public class MyAnnotationConfigApplicationContext implements MyApplicationContext {

     private  Map<String,Object> mapbean = new HashMap<String, Object>();


    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException, ClassNotFoundException {

        register(componentClasses);
            
    }

    private void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        if(componentClasses==null||componentClasses.length<=0){
            throw new RuntimeException("没有指定配置类") ;
        }
        for (Class cls:componentClasses){
            if (!cls.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }
            String [] basePackages = getAppConfigBasePackages(cls);
            if (cls.isAnnotationPresent(MyComponentScan.class)){
                MyComponentScan mcs= (MyComponentScan) cls.getAnnotation(MyComponentScan.class);
                if (mcs.basePackages()!=null&&mcs.basePackages().length>0){
                    basePackages=mcs.basePackages();
                }
            }
            Object obj = cls.newInstance();
            handLeAtMyBean(cls,obj);


            //处理   basePackages 基础包下的所有的托管bean
            for(String basePackage:basePackages){
                scanPackageAndSubPackageClasses(basePackage);
            }

            handleManageBean();

            handleDi(mapbean);


        }
    }

    private void handleDi(Map<String, Object> mapbean) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection = mapbean.values();
        for(Object obj:objectCollection){
            Class cl =obj.getClass();
            Method [] m = cl.getDeclaredMethods();
            for (Method ms:m){
                if(ms.isAnnotationPresent(MyAutowired.class)&&ms.getName().startsWith("set")){

                    invokeAutowiredMethod(ms,obj);
                }else if(ms.isAnnotationPresent(MyResourse.class)&&ms.getName().startsWith("set")){
                    invokeResourseMethod(ms,obj);
                }
            }
            Field[] f = cl.getDeclaredFields();
            for (Field fs:f){
                if(fs.isAnnotationPresent(MyAutowired.class)){

                }else if(fs.isAnnotationPresent(MyResourse.class)){

                }
            }

        }
    }

    private void invokeResourseMethod(Method ms, Object obj) throws InvocationTargetException, IllegalAccessException {
        MyResourse mr = ms.getAnnotation(MyResourse.class);
        String beanId = mr.name();
        if (beanId==null||beanId.equalsIgnoreCase("")){
            String pname = ms.getParameterTypes()[0].getSimpleName();
            beanId=pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        Object o = mapbean.get(beanId);
        ms.invoke(obj,o);
    }

    private void invokeAutowiredMethod(Method ms, Object obj) throws InvocationTargetException, IllegalAccessException {
        Class typeclass = ms.getParameterTypes()[0];
        Set<String> keys = mapbean.keySet();
        for(String key:keys){
            Object o = mapbean.get(key);
            if(o.getClass().getName().equalsIgnoreCase(typeclass.getName())){
                ms.invoke(obj,o);
            }
        }

    }

    private void handleManageBean() throws IllegalAccessException, InstantiationException, InvocationTargetException {

        for (Class c :manageBeanClasses){
            if(c.isAnnotationPresent(MyComponent.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyService.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyRepository.class)){
                saveManagedBean(c);
            }else if (c.isAnnotationPresent(MyController.class)){
                saveManagedBean(c);
            }else {
                continue;
            }
        }
    }

    private void saveManagedBean(Class c) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object o = c.newInstance();
        handLePostConstruct(o,c);
        String beanID=c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        mapbean.put(beanID,o);
    }

    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath = basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径："+basePackage+"，替换后:"+packagePath);
        Enumeration<URL> files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (files.hasMoreElements()){
            URL url =files.nextElement();
            System.out.println("配置的扫描路径:"+url.getFile());
            //TODO:递归这些目录，查找 .class文件
            findClassInPackages(url.getFile(),basePackage);
        }

    }

    private Set<Class> manageBeanClasses = new HashSet<Class>();
    private void findClassInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f = new File(file);

        File[] classFiles = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class")||file.isDirectory();
            }
        });
        for (File cfc:classFiles){
            if (cfc.isDirectory()){
                basePackage+="." +cfc.getName().substring(cfc.getName().lastIndexOf("/"+1));
                findClassInPackages(cfc.getAbsolutePath(),basePackage);
            }else {
                URL[]urls = new URL[]{};
                URLClassLoader url = new URLClassLoader(urls);
                Class c= url.loadClass(basePackage+"."+cfc.getName().replace(".class",""));
                manageBeanClasses.add(c);

            }
        }
    }

    private void handLeAtMyBean(Class cl,Object obj) throws InvocationTargetException, IllegalAccessException {
        Method [] m = cl.getDeclaredMethods();

        for(Method ms:m){
            if (ms.isAnnotationPresent(MyBean.class)){
                Object o =ms.invoke(obj);
                handLePostConstruct(o,o.getClass());
                mapbean.put(ms.getName(),o);
            }

        }
    }

    private void handLePostConstruct(Object o, Class<?> aClass) throws InvocationTargetException, IllegalAccessException {
        Method[] m = aClass.getDeclaredMethods();
        for(Method ms:m){
            if (ms.isAnnotationPresent(MyPostConstruct.class)){
                ms.invoke(o);
            }

        }
    }

    private String[] getAppConfigBasePackages(Class cl) {
        String [] paths = new String[1];
        paths[0] =cl.getPackage().getName();
        return  paths;
    }


    @Override
    public Object getBean(String id) {
        return mapbean.get(id);
    }
}
