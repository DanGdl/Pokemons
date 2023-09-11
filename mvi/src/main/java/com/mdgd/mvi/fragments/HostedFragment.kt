package com.mdgd.mvi.fragments

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.mdgd.mvi.states.ScreenState
import java.lang.reflect.ParameterizedType

abstract class HostedFragment<
        VIEW : FragmentContract.View,
        VIEW_MODEL : FragmentContract.ViewModel<VIEW>,
        HOST : FragmentContract.Host>
    : NavHostFragment(), FragmentContract.View, Observer<ScreenState<VIEW>>,
    LifecycleEventObserver {

    protected var viewModel: VIEW_MODEL? = null
        private set

    protected var fragmentHost: HOST? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // keep the call back
        fragmentHost = try {
            context as HOST
        } catch (e: Throwable) {
            val hostClassName = ((javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[1] as Class<*>).canonicalName
            throw RuntimeException(
                "Activity must implement $hostClassName to attach ${this.javaClass.simpleName}", e
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        // release the call back
        fragmentHost = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setModel(createModel())
        lifecycle.addObserver(this)
        viewModel?.let {
            it.getStateObservable().observe(this, this)
            it.getEffectObservable().observe(this, this)
        }
    }

    protected abstract fun createModel(): VIEW_MODEL

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        viewModel?.onStateChanged(event)

        if (lifecycle.currentState <= Lifecycle.State.DESTROYED) {
            lifecycle.removeObserver(this)
        }
    }

    override fun onChanged(value: ScreenState<VIEW>) {
        value.visit(this@HostedFragment as VIEW)
    }

    protected fun setModel(model: VIEW_MODEL) {
        this.viewModel = model
    }
}
