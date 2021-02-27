package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val cache: Cache) : MviViewModel<SplashScreenState>(), SplashContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        setState(SplashScreenState.ShowError(e))
    }

    private var progressJob: Job? = null

    public override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START && progressJob == null) {
            progressJob = viewModelScope.launch(exceptionHandler) {
                delay(SplashContract.SPLASH_DELAY)
                val value = cache.getProgressChanel().receive()
                if (value.isError()) {
                    setState(SplashScreenState.ShowError(value.getError()))
                } else if (value.getValue() != 0L) {
                    setState(SplashScreenState.NextScreen)
                }
            }
            setState(SplashScreenState.LaunchWorker)
        }
    }

    override fun getDefaultState(): SplashScreenState {
        return SplashScreenState.None
    }

    override fun onCleared() {
        super.onCleared()
        progressJob = null
    }
}
