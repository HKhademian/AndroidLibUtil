@file:JvmName("ViewModelUtils")
@file:Suppress("PackageDirectoryMismatch")

package ir.hossainco.utils.arch.viewModels

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders


/* normal viewModel */
inline fun <reified T : ViewModel> Fragment.viewModel() =
	ViewModelProviders.of(this)[T::class.java]

/* normal viewModel */
inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
	ViewModelProviders.of(this)[T::class.java]


/* custom factory viewModel */
inline fun <reified T : ViewModel> Fragment.viewModel(noinline factory: () -> T) =
	ViewModelProviders.of(this, factory(factory))[T::class.java]

/* custom factory viewModel */
inline fun <reified T : ViewModel> FragmentActivity.viewModel(noinline factory: () -> T) =
	ViewModelProviders.of(this, factory(factory))[T::class.java]

/* custom factory */
fun <T : ViewModel> factory(factory: () -> T) =
	object : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T = factory() as T
	}


/* injected factory */
inline fun <reified T : ViewModel> factoryOf() =
	factory { T::class.java.newInstance() }

/* injected viewModel */
inline fun <reified T : ViewModel> Fragment.viewModelOf() =
	ViewModelProviders.of(this, factoryOf<T>())[T::class.java]

/* injected viewModel */
inline fun <reified T : ViewModel> FragmentActivity.viewModelOf() =
	ViewModelProviders.of(this, factoryOf<T>())[T::class.java]
