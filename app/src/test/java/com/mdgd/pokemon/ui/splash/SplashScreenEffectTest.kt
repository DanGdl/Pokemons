package com.mdgd.pokemon.ui.splash

import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class SplashScreenEffectTest {

    private lateinit var view: SplashContract.View

    @Before
    fun setup() {
        view = Mockito.mock(SplashContract.View::class.java)
    }

    private fun verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(view)
    }

    @Test
    fun testLaunchWorkerState() {
        SplashScreenEffect.LaunchWorker.visit(view)

        Mockito.verify(view, Mockito.times(1)).launchWorker()
        verifyNoMoreInteractions()
    }

    @Test
    fun testErrorState() {
        val error = Throwable("TestError")
        val errorCaptor = ArgumentCaptor.forClass(Throwable::class.java)
        SplashScreenEffect.ShowError(error).visit(view)

        Mockito.verify(view, Mockito.times(1)).showError(errorCaptor.capture())
        Assert.assertEquals(error, errorCaptor.value)

        verifyNoMoreInteractions()
    }

    @Test
    fun testProceedToNextState() {
        SplashScreenEffect.NextScreen.visit(view)

        Mockito.verify(view, Mockito.times(1)).proceedToNextScreen()
        verifyNoMoreInteractions()
    }
}
