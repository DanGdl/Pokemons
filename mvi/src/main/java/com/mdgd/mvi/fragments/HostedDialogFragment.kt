package com.mdgd.mvi.fragments

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import com.mdgd.mvi.states.AbstractAction
import com.mdgd.mvi.states.ScreenState
import java.lang.reflect.ParameterizedType

abstract class HostedDialogFragment<
        VIEW : FragmentContract.View,
        STATE : ScreenState<VIEW, STATE>,
        ACTION : AbstractAction<VIEW>,
        VIEW_MODEL : FragmentContract.ViewModel<STATE, ACTION>,
        HOST : FragmentContract.Host>
    : AppCompatDialogFragment(), FragmentContract.View, Observer<STATE> {

    protected var model: VIEW_MODEL? = null
        private set

    protected var fragmentHost: HOST? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // keep the call back
        try {
            fragmentHost = context as HOST
        } catch (e: Throwable) {
            val hostClassName = ((javaClass.genericSuperclass as ParameterizedType)
                    .actualTypeArguments[1] as Class<*>).canonicalName
            throw RuntimeException("Activity must implement " + hostClassName
                    + " to attach " + javaClass.simpleName, e)
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
        if (model != null) {
            lifecycle.addObserver(model!!)
            model!!.getStateObservable().observe(this, this)
            model!!.getActionObservable().observe(this, { action ->
                action.visit(this as VIEW)
            })
        }
    }

    override fun onChanged(state: STATE) {
        state.visit(this as VIEW)
    }

    override fun onDestroy() {
        // order matters
        if (model != null) {
            model!!.getActionObservable().removeObservers(this)
            model!!.getStateObservable().removeObservers(this)
            lifecycle.removeObserver(model!!)
        }
        super.onDestroy()
    }

    protected abstract fun createModel(): VIEW_MODEL?

    protected fun hasHost(): Boolean {
        return fragmentHost != null
    }

    protected fun setModel(model: VIEW_MODEL?) {
        this.model = model
    }
}
