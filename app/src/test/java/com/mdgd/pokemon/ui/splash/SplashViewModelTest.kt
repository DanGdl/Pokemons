package com.mdgd.pokemon.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect
import com.mdgd.pokemon.ui.splash.state.SplashScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class SplashViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var model: SplashViewModel
    private lateinit var cache: Cache

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        cache = Mockito.mock(Cache::class.java)
        model = SplashViewModel(cache)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(cache)
    }

    @Test
    fun testSetup_NotingHappened() = runBlockingTest {
        val stateObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        model.getStateObservable().observeForever(stateObserverMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenEffect>
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
    fun testSetup_LaunchError() = runBlockingTest {
        val error = Throwable("TestError")
        val progressChanel = Channel<Result<Long>>(Channel.Factory.CONFLATED)
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val stateObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        model.getStateObservable().observeForever(stateObserverMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenEffect>
        val actionCaptor = ArgumentCaptor.forClass(SplashScreenEffect::class.java)
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.value is SplashScreenEffect.LaunchWorker)

        progressChanel.send(Result(error))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorState = actionCaptor.value
        Assert.assertTrue(errorState is SplashScreenEffect.ShowError)
        Assert.assertEquals((errorState as SplashScreenEffect.ShowError).e, error)

        Mockito.verifyNoInteractions(stateObserverMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(stateObserverMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchCrash() = runBlockingTest {
        val error = RuntimeException("TestError")
        Mockito.`when`(cache.getProgressChanel()).thenThrow(error)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenEffect>
        val actionCaptor = ArgumentCaptor.forClass(SplashScreenEffect::class.java)
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.value is SplashScreenEffect.LaunchWorker)

        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorState = actionCaptor.value
        Assert.assertTrue(errorState is SplashScreenEffect.ShowError)
        Assert.assertEquals((errorState as SplashScreenEffect.ShowError).e, error)

        Mockito.verifyNoInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }

    @Test
    fun testSetup_LaunchOk() = runBlockingTest {
        val progressChanel = Channel<Result<Long>>(Channel.Factory.CONFLATED)
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        model.getStateObservable().observeForever(observerMock)

        val actionObserverMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenEffect>
        val actionCaptor = ArgumentCaptor.forClass(SplashScreenEffect::class.java)
        model.getEffectObservable().observeForever(actionObserverMock)


        model.onStateChanged(Lifecycle.Event.ON_START)


        Mockito.verify(actionObserverMock, Mockito.times(1)).onChanged(actionCaptor.capture())
        Assert.assertTrue(actionCaptor.value is SplashScreenEffect.LaunchWorker)

        progressChanel.send(Result(90L))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(actionObserverMock, Mockito.times(2)).onChanged(actionCaptor.capture())
        val errorState = actionCaptor.value
        Assert.assertTrue(errorState is SplashScreenEffect.NextScreen)


        Mockito.verifyNoInteractions(observerMock)
        Mockito.verifyNoMoreInteractions(actionObserverMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
        model.getEffectObservable().removeObserver(actionObserverMock)
    }
}
