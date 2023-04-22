package com.mdgd.pokemon.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.MainDispatcherRule
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var model: SplashViewModel
    private lateinit var cache: Cache

    @Before
    fun setup() {
        cache = Mockito.mock(Cache::class.java)
        model = SplashViewModel(cache)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(cache)
    }

    @Test
    fun testSetup_NotingHappened() = runBlocking {
        val stateObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        model.getStateObservable().observeForever(stateObserverMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        model.getEffectObservable().observeForever(actionObserverMock)

        model.onStateChanged(Lifecycle.Event.ON_CREATE)
        model.onStateChanged(Lifecycle.Event.ON_RESUME)
        model.onStateChanged(Lifecycle.Event.ON_PAUSE)
        model.onStateChanged(Lifecycle.Event.ON_STOP)
        model.onStateChanged(Lifecycle.Event.ON_DESTROY)
        model.onStateChanged(Lifecycle.Event.ON_ANY)

        Mockito.verifyNoMoreInteractions(stateObserverMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(stateObserverMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlocking {
        val error = Throwable("TestError")
        val progressChanel = MutableSharedFlow<Result<Long>>(
            extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val stateObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        model.getStateObservable().observeForever(stateObserverMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        val actionCaptor = argumentCaptor<SplashScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)

        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.firstValue is SplashScreenEffect.LaunchWorker)

        progressChanel.tryEmit(Result(error))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorState = actionCaptor.thirdValue
        Assert.assertTrue(errorState is SplashScreenEffect.ShowError)
        Assert.assertEquals((errorState as SplashScreenEffect.ShowError).e, error)

        Mockito.verifyNoInteractions(stateObserverMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(stateObserverMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchCrash() = runBlocking {
        val error = RuntimeException("TestError")
        Mockito.`when`(cache.getProgressChanel()).thenThrow(error)

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        val actionCaptor = argumentCaptor<SplashScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)


        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorEffect = actionCaptor.allValues
        Assert.assertTrue(errorEffect[0] is SplashScreenEffect.ShowError)
        Assert.assertEquals((errorEffect[0] as SplashScreenEffect.ShowError).e, error)
        Assert.assertTrue(errorEffect[1] is SplashScreenEffect.LaunchWorker)

        Mockito.verifyNoInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchOk() = runBlocking {
        val progressChanel = MutableSharedFlow<Result<Long>>(
            extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val observerMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock =
            Mockito.mock(Observer::class.java) as Observer<ScreenState<SplashContract.View>>
        val actionCaptor = argumentCaptor<SplashScreenEffect>()
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.firstValue is SplashScreenEffect.LaunchWorker)

        progressChanel.tryEmit(Result(90L))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorState = actionCaptor.thirdValue
        Assert.assertTrue(errorState is SplashScreenEffect.NextScreen)


        Mockito.verifyNoInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }
}
