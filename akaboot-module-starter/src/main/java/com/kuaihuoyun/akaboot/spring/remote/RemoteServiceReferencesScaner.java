package com.kuaihuoyun.akaboot.spring.remote;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RemoteServiceReferencesScaner {

    public static List<Class<?>> doScan(String basePackage, String classNameRegex) throws IOException,
            ClassNotFoundException {
        if (basePackage == null || basePackage.isEmpty()) { //如果包名为空，则不进行查找
            return new ArrayList<>();
        }
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
                resourcePatternResolver);
        List<Class<?>> candidates = new ArrayList<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + resolveBasePackage(basePackage) + "/" + "**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        Pattern pattern = null;
        if (classNameRegex != null && !classNameRegex.isEmpty()) {
            pattern = Pattern.compile(classNameRegex);
        }
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory
                        .getMetadataReader(resource);
                Class<?> clz = Class.forName(metadataReader.getClassMetadata().getClassName());
                //todo scan all annotated fields in class
            }
        }
        return candidates;
    }

    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils
                .resolvePlaceholders(basePackage));
    }

}
