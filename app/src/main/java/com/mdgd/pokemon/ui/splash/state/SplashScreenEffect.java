package com.mdgd.pokemon.ui.splash.state;

import com.mdgd.mvi.states.AbstractEffect;
import com.mdgd.pokemon.ui.splash.SplashContract;

public class SplashScreenEffect extends AbstractEffect<SplashContract.View> {

    public static class LaunchWorkerEffect extends SplashScreenEffect {

        public LaunchWorkerEffect() {
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.launchWorker();
        }
    }

    public static class ShowErrorEffect extends SplashScreenEffect {

        private final Throwable error;

        public ShowErrorEffect(Throwable e) {
            error = e;
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.showError(error);
        }
    }

    public static class ProceedToNextScreeEffect extends SplashScreenEffect {

        public ProceedToNextScreeEffect() {
        }

        @Override
        protected void handle(SplashContract.View screen) {
            screen.proceedToNextScreen();
        }
    }
}
