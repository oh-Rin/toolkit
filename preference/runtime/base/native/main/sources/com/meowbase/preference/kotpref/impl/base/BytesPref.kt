package com.meowbase.preference.kotpref.impl.base

import android.content.SharedPreferences
import androidx.annotation.RestrictTo
import com.meowbase.preference.core.get
import com.meowbase.preference.core.put
import com.meowbase.preference.kotpref.KotprefModel

class BytesPref(
  val default: ByteArray,
  override val key: String?,
  override val commitByDefault: Boolean,
  override val getterImplProvider: ((thisRef: KotprefModel, preference: SharedPreferences) -> ByteArray)? = null,
  override val putterImplProvider: ((thisRef: KotprefModel, value: ByteArray, editor: SharedPreferences.Editor) -> SharedPreferences.Editor)? = null
) : AbstractPref<ByteArray>() {
  override fun get(thisRef: KotprefModel, preference: SharedPreferences): ByteArray =
    preference.get(key, default)

  override fun put(
    thisRef: KotprefModel,
    value: ByteArray,
    editor: SharedPreferences.Editor
  ): SharedPreferences.Editor = editor.put(key, value)
}
