package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.ui.splash.state.SplashScreenAction
import com.mdgd.pokemon.ui.splash.state.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val cache: Cache) :
    MviViewModel<SplashContract.View, SplashScreenState, SplashScreenAction>(),
    SplashContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        setAction(SplashScreenAction.ShowError(e))
    }

    private var progressJob: Job? = null

    public override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
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
                        setAction(SplashScreenAction.ShowError(it.getError()))
                    } else if (it.getValue() != 0L) {
                        setAction(SplashScreenAction.NextScreen)
                    }
                }
            }
            setAction(SplashScreenAction.LaunchWorker)
        }
    }

    override fun onCleared() {
        super.onCleared()
        progressJob = null
    }
}
