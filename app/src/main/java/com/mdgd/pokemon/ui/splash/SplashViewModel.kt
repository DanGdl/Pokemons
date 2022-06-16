package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect
import com.mdgd.pokemon.ui.splash.state.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val cache: Cache) :
    MviViewModel<SplashContract.View, SplashScreenState, SplashScreenEffect>(),
    SplashContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        setEffect(SplashScreenEffect.ShowError(e))
    }

    private var progressJob: Job? = null

    override fun onStateChanged(event: Lifecycle.Event) {
        super.onStateChanged(event)
        if (event == Lifecycle.Event.ON_START && progressJob == null) {
            progressJob = viewModelScope.launch(exceptionHandler) {
                flow {
                    delay(SplashContract.SPLASH_DELAY)
                    emit(System.currentTimeMillis())
                }.combine(cache.getProgressChanel()) { _: Long, result: Result<Long> ->
                    result
                    // Result<Long>(Throwable("Dummy"))
                }.collect {
                    if (it.isError()) {
                        setEffect(SplashScreenEffect.ShowError(it.getError()))
                    } else if (it.getValue() != 0L) {
                        setEffect(SplashScreenEffect.NextScreen)
                    }
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
