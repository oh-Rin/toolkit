package com.meowbase.gradle.plugin.toolkit.booster.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.meowbase.gradle.plugin.toolkit.ktx.getAndroid
import com.meowbase.gradle.plugin.toolkit.ktx.getProperty
import org.gradle.api.Project

/**
 * Represents the transform base
 *
 * @author johnsonlee
 */
open class BoosterTransform(
  val project: Project,
  val transformers: List<Transformer>
) : Transform() {

  internal val verifyEnabled = project.getProperty(OPT_TRANSFORM_VERIFY, false)

  private val android: BaseExtension = project.getAndroid()

  private lateinit var androidKlassPool: AbstractKlassPool

  init {
    project.afterEvaluate {
      androidKlassPool = object : AbstractKlassPool(android.bootClasspath) {}
    }
  }

  val bootKlassPool: AbstractKlassPool
    get() = androidKlassPool

  override fun getName() = "booster"

  override fun isIncremental() = !verifyEnabled

  override fun isCacheable() = !verifyEnabled

  override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

  override fun getScopes(): MutableSet<in QualifiedContent.Scope> = when {
    transformers.isEmpty() -> mutableSetOf()
    project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
    project.plugins.hasPlugin("com.android.application") -> SCOPE_FULL_PROJECT
    project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
    else -> TODO("Not an Android project")
  }

  override fun getReferencedScopes(): MutableSet<in QualifiedContent.Scope> = when {
    transformers.isEmpty() -> when {
      project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
      project.plugins.hasPlugin("com.android.application") -> SCOPE_FULL_PROJECT
      project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
      else -> TODO("Not an Android project")
    }
    else -> super.getReferencedScopes()
  }

  final override fun transform(invocation: TransformInvocation) {
    BoosterTransformInvocation(invocation, this).apply {
      if (isIncremental) {
        doIncrementalTransform()
      } else {
        outputProvider?.deleteAll()
        doFullTransform()
      }
    }
  }

}

/**
 * The option for transform outputs verifying, default is false
 */
private const val OPT_TRANSFORM_VERIFY = "booster.transform.verify"
