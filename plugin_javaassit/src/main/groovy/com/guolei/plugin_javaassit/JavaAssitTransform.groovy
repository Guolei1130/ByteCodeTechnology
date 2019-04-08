package com.guolei.plugin_javaassit

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project

class JavaAssitTransform extends Transform {

    Project mProject

    JavaAssitTransform(Project project) {
        mProject = project
        JavaAssitProcessor.appendAndroidSDK(project)
    }

    @Override
    String getName() {
        return "JavaAssitTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental)
            throws IOException, TransformException, InterruptedException {
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                JavaAssitProcessor.appendDir(directoryInput.file.absolutePath)
            }
            input.jarInputs.each { JarInput jarInput ->
                JavaAssitProcessor.appendJar(jarInput.file)
            }
        }

        inputs.each { TransformInput input ->

            input.directoryInputs.each { DirectoryInput directoryInput ->
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.getAllFiles().each {
//                    it-> println(it.absolutePath)
                }
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            input.jarInputs.each { JarInput jarInput ->
                def dest = outputProvider.getContentLocation(jarInput.name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                if (jarInput.file.absolutePath.concat("com.squareup.okhttp3")) {
                    def outFile = JavaAssitProcessor.processJar(jarInput.file,mProject)
                }else {
                    FileUtils.copyFile(jarInput.file, dest)
                }
            }

        }

    }
}