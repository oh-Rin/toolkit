package com.meowbase.gradle.plugin.preference.data

import org.gradle.api.Project

val Project.tempDir get() = rootProject.buildDir.resolve(".preference-temp")