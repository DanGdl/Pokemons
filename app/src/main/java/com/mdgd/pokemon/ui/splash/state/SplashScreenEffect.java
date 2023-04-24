package com.mdgd.pokemon.ui.splash.state;

import com.mdgd.mvi.states.AbstractEffect;
import com.mdgd.pokemon.ui.splash.SplashContract;

public class SplashScreenEffect extends AbstractEffect<SplashContract.View> {

    protected final Throwable error;

    public SplashScreenEffect(Throwable e) {
        error = e;
    }


    public static class LaunchWorkerEffect extends SplashScreenEffect {

        public LaunchWorkerEffect() {
            super(null);
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.launchWorker();
        }
    }

    public static class ShowErrorEffect extends SplashScreenEffect {

        public ShowErrorEffect(Throwable e) {
            super(e);
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.showError(error);
        }
    }

    public static class ProceedToNextScreeEffect extends SplashScreenEffect {

        public ProceedToNextScreeEffect() {
            super(null);
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.proceedToNextScreen();
        }
    }
}
