package com.kuaihuoyun.akaboot.spring.remote;

import com.google.common.collect.Sets;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.Set;

public class RemoteServiceReferencesScaner {

    private static Set<String> registeredRemoteClasses = Sets.newHashSet();

    public static void doScan(String basePackage, RemoteServiceRegistryCollector registryCollector) throws IOException,
            ClassNotFoundException {
        if (basePackage == null || basePackage.isEmpty()) { //如果包名为空，则不进行查找
            return;
        }
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + resolveBasePackage(basePackage) + "/" + "**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String className = metadataReader.getClassMetadata().getClassName();
                if(!registeredRemoteClasses.contains(className)){
                    Class<?> clz = Class.forName(className);
                    registryCollector.tryCollecting(clz);
                    registeredRemoteClasses.add(className);
                }


            }
        }
    }

    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }

}
