package com.guolei.plugin_javaassit

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.apache.commons.compress.utils.IOUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry


class JavaAssitProcessor {
    static ClassPool sClassPool = ClassPool.getDefault()

    static void appendAndroidSDK(Project project){
        sClassPool.appendClassPath(project.android.bootClasspath[0].toString())
    }

    static void appendDir(File dir) {
        sClassPool.insertClassPath(dir.absolutePath)
    }

    static void appendJar(File jarFile) {
        sClassPool.insertClassPath(jarFile.absolutePath)
    }

    static File processJar(File jarFile,Project project){
        def outDir = new File(project.buildDir, "rookieConfigOutput")
        if (!outDir.exists()) {
            outDir.mkdir()
        }
        outDir.listFiles().each {
            it -> it.delete()
        }
        def processJar = new File(outDir, jarFile.name)
        def jarOutputStream = new JarOutputStream(new FileOutputStream(processJar))
        def file = new JarFile(jarFile)
        Enumeration enumeration = file.entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement()
            String entryName = jarEntry.name
            ZipEntry zipEntry = new ZipEntry(entryName)

            InputStream inputStream = file.getInputStream(jarEntry)
            jarOutputStream.putNextEntry(zipEntry)
            if (entryName.equals("okhttp3/RealCall.class")) {
                def result = modifyClass(inputStream)
                jarOutputStream.write(result)
            } else {
                jarOutputStream.write(IOUtils.toByteArray(inputStream))
            }
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        file.close()
        return processJar
    }

    private static byte[] modifyClass(InputStream inputStream) {
        CtClass ctClass = sClassPool.makeClass(inputStream,false)
        sClassPool.importPackage("okhttp3.internal.http")
        sClassPool.importPackage("okhttp3.internal.cache")
        sClassPool.importPackage("cn.com.vipkid.libs.rookieconfig.core.http")
        sClassPool.importPackage("okhttp3.internal.connection")
        sClassPool.importPackage("java.lang")
        sClassPool.importPackage("java.util")

        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }
        CtMethod ctMethod = ctClass.getDeclaredMethod("getResponseWithInterceptorChain")
        ctMethod.setBody(new ContentReader().read())
        return ctClass.toBytecode()
    }



}