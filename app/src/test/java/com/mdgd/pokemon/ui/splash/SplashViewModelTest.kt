package com.mdgd.pokemon.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
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
    fun testSetup_NotingHappened() {
        val observerMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        model.getStateObservable().observeForever(observerMock)

        model.onAny(null, Lifecycle.Event.ON_CREATE)
        model.onAny(null, Lifecycle.Event.ON_RESUME)
        model.onAny(null, Lifecycle.Event.ON_PAUSE)
        model.onAny(null, Lifecycle.Event.ON_STOP)
        model.onAny(null, Lifecycle.Event.ON_DESTROY)
        model.onAny(null, Lifecycle.Event.ON_ANY)

        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
    }

    @Test
    fun testSetup_LaunchError() = runBlocking {
        val error = Throwable("TestError")
        val progressChanel = Channel<Result<Long>>(Channel.Factory.CONFLATED)
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        val stateCaptor = ArgumentCaptor.forClass(SplashScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)


        model.onAny(null, Lifecycle.Event.ON_START)


        Mockito.verify(observerMock, Mockito.times(1)).onChanged(stateCaptor.capture())
        Assert.assertTrue(stateCaptor.value is SplashScreenState.LaunchWorker)

        progressChanel.send(Result(error))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(observerMock, Mockito.times(2)).onChanged(stateCaptor.capture())
        val errorState = stateCaptor.value
        Assert.assertTrue(errorState is SplashScreenState.ShowError)
        Assert.assertEquals((errorState as SplashScreenState.ShowError).e, error)


        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
    }

    @Test
    fun testSetup_LaunchOk() = runBlocking {
        val progressChanel = Channel<Result<Long>>(Channel.Factory.CONFLATED)
        Mockito.`when`(cache.getProgressChanel()).thenReturn(progressChanel)

        val observerMock = Mockito.mock(Observer::class.java) as Observer<SplashScreenState>
        val stateCaptor = ArgumentCaptor.forClass(SplashScreenState::class.java)
        model.getStateObservable().observeForever(observerMock)


        model.onAny(null, Lifecycle.Event.ON_START)


        Mockito.verify(observerMock, Mockito.times(1)).onChanged(stateCaptor.capture())
        Assert.assertTrue(stateCaptor.value is SplashScreenState.LaunchWorker)

        progressChanel.send(Result(90L))
        Thread.sleep(SplashContract.SPLASH_DELAY * 2)
        Mockito.verify(cache, Mockito.times(1)).getProgressChanel()
        Mockito.verify(observerMock, Mockito.times(2)).onChanged(stateCaptor.capture())
        val errorState = stateCaptor.value
        Assert.assertTrue(errorState is SplashScreenState.NextScreen)


        Mockito.verifyNoMoreInteractions(observerMock)
        verifyNoMoreInteractions()
        model.getStateObservable().removeObserver(observerMock)
    }
}
