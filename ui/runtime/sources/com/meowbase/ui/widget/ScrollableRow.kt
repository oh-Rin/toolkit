@file:Suppress("FunctionName")

package com.meowbase.ui.widget

import android.widget.LinearLayout
import com.meowbase.ui.Ui
import com.meowbase.ui.core.CrossAxisAlignment
import com.meowbase.ui.core.MainAxisAlignment
import com.meowbase.ui.core.Modifier
import com.meowbase.ui.widget.implement.*
import com.meowbase.ui.widget.modifier.wrapContent


/** 可滚动的行视图 [Ui.Row] */
fun Ui.ScrollableRow(
  /** 允许滚动 */
  enabled: Boolean = true,
  /** 其他更多的可选调整 */
  modifier: Modifier = Modifier,
  /** 让元素填满滚动视图 */
  fillViewport: Boolean = true,
  /** 子内容的水平方向对齐 */
  mainAxisAlign: MainAxisAlignment = MainAxisAlignment.Start,
  /** 子内容的垂直方向对齐 */
  crossAxisAlign: CrossAxisAlignment = CrossAxisAlignment.Start,
  /** 子控件权重总数，这会影响每个子控件的 [LinearLayout.LayoutParams.weight] */
  weightSum: Number? = null,
  children: Row.() -> Unit
): ScrollableRow = With(::ScrollableRow) {
  it.isFillViewport = fillViewport
  it.row = it.Row(
    mainAxisAlign = mainAxisAlign,
    crossAxisAlign = crossAxisAlign,
    weightSum = weightSum,
    children = children
  )
  it.update(enabled, modifier, mainAxisAlign, crossAxisAlign, weightSum)
}