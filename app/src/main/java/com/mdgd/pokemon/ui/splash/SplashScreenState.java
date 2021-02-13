package com.mdgd.pokemon.ui.splash;

import com.mdgd.mvi.ScreenState;

public class SplashScreenState extends ScreenState<SplashContract.View> {

    private static final int ACTION_NONE = 0;
    private static final int ACTION_LAUNCH_WORKER = 1;
    private static final int ACTION_SHOW_ERROR = 2;
    private static final int ACTION_NEXT_SCREEN = 3;
    private final int action;
    private final Throwable error;

    public SplashScreenState(int action, Throwable e) {
        this.action = action;
        error = e;
    }

    public static SplashScreenState createLaunchWorkerState() {
        return new SplashScreenState(ACTION_LAUNCH_WORKER, null);
    }

    public static SplashScreenState createShowError(Throwable e) {
        return new SplashScreenState(ACTION_SHOW_ERROR, e);
    }

    public static SplashScreenState createNextScreen() {
        return new SplashScreenState(ACTION_NEXT_SCREEN, null);
    }

    public static SplashScreenState createDefaultState() {
        return new SplashScreenState(ACTION_NONE, null);
    }

    @Override
    public void visit(SplashContract.View screen) {
        if (ACTION_LAUNCH_WORKER == action) {
            screen.launchWorker();
        } else if (ACTION_SHOW_ERROR == action) {
            screen.showError(error);
        } else if (ACTION_NEXT_SCREEN == action) {
            screen.proceedToNextScreen();
        }
    }
}
