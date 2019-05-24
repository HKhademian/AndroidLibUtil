package ir.hossainco.utils.view

import android.content.Context
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

fun <T : View> ViewManager.add(
	view: T,
	init: T.() -> Unit = {}
) =
	ankoView({ view }, theme = 0, init = init)

fun <T : View> ViewManager.factory(
	factory: (Context) -> T,
	theme: Int = 0,
	init: T.() -> Unit = {}
) =
	ankoView(factory, theme = theme, init = init)
