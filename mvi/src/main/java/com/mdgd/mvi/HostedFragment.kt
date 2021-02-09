package com.mdgd.mvi

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import java.lang.reflect.ParameterizedType

abstract class HostedFragment<
        STATE : ScreenState<*>,
        VIEW_MODEL : FragmentContract.ViewModel<STATE>,
        HOST : FragmentContract.Host>
    : NavHostFragment(), FragmentContract.View, Observer<STATE> {

    protected var model: VIEW_MODEL? = null
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
            throw RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + this.javaClass.simpleName, e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setModel(createModel())
        if (model != null) {
            lifecycle.addObserver(model!!)
            model!!.getStateObservable().observe(this, this)
        }
    }

    protected abstract fun createModel(): VIEW_MODEL

    override fun onDetach() {
        super.onDetach()
        // release the call back
        fragmentHost = null
    }

    override fun onDestroy() {
        // order matters
        if (model != null) {
            model!!.getStateObservable().removeObservers(this)
            lifecycle.removeObserver(model!!)
        }
        super.onDestroy()
    }

    abstract override fun onChanged(screenState: STATE);

//    override fun onChanged(screenState: STATE) {
//        screenState.visit(this)
//    }

    protected fun hasHost(): Boolean {
        return fragmentHost != null
    }

    protected fun setModel(model: VIEW_MODEL) {
        this.model = model
    }
}
