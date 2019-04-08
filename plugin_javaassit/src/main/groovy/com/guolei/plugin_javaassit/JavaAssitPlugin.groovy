package com.guolei.plugin_javaassit

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class JavaAssitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin("com.android.application")) {
            def android = project.extensions.getByType(AppExtension)
            def transform = new JavaAssitTransform(project)
            android.registerTransform(transform)
        }
    }
}