package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import kotlinx.coroutines.*

class SplashViewModel(private val router: SplashContract.Router, private val cache: Cache) : MviViewModel<SplashScreenState>(), SplashContract.ViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, e ->
        router.showError(e)
    }
    private var progressJob: Job? = null

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START && progressJob == null) {
            progressJob = viewModelScope.launch(exceptionHandler) {
                val async = async(exceptionHandler) {
                    delay(1000)
                    cache.getProgressChanel().receive()
                }
                val value = async.await()
                if (value.isError()) {
                    router.showError(value.getError())
                } else if (value.getValue() != 0L) {
                    router.proceedToNextScreen()
                }
            }
            router.launchWorker()
        }
    }
}
