package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val cache: Cache
) : MviViewModel<SplashContract.View>(), SplashContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        setEffect(SplashScreenEffect.ShowError(e))
    }

    private var progressJob: Job? = null

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)

        if (event == Lifecycle.Event.ON_START && progressJob == null) {
            progressJob = viewModelScope.launch(exceptionHandler) {
                delay(SplashContract.SPLASH_DELAY)
                val value = cache.getProgressChanel().receive()
                if (value.isError()) {
                    setEffect(SplashScreenEffect.ShowError(value.getError()))
                } else if (value.getValue() != 0L) {
                    setEffect(SplashScreenEffect.NextScreen)
                }
            }
            setEffect(SplashScreenEffect.LaunchWorker)
        }
    }

    override fun onCleared() {
        super.onCleared()
        progressJob = null
    }
}
