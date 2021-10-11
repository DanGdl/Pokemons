package com.mdgd.pokemon.ui.splash

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.bg.UploadWorker
import com.mdgd.pokemon.ui.splash.state.SplashScreenAction
import com.mdgd.pokemon.ui.splash.state.SplashScreenState

class SplashFragment :
    HostedFragment<SplashContract.View, SplashScreenState, SplashScreenAction, SplashContract.ViewModel, SplashContract.Host>(),
    SplashContract.View {

    override fun createModel(): SplashContract.ViewModel {
        return ViewModelProvider(
            this, SplashViewModelFactory(PokemonsApp.instance?.appComponent!!)
        ).get(SplashViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.splash_compose_root)?.setContent {
            SplashScreen()
        }
    }

    override fun proceedToNextScreen() {
        if (hasHost()) {
            fragmentHost!!.proceedToPokemonsScreen()
        }
    }

    override fun launchWorker() {
        if (hasHost()) {
            WorkManager.getInstance(requireContext())
                .enqueue(OneTimeWorkRequest.Builder(UploadWorker::class.java).build())
        }
    }

    override fun showError(error: Throwable?) {
        if (hasHost()) {
            fragmentHost!!.showError(error)
        }
    }
}

@Composable
fun SplashScreen() {
    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_splash),
                contentDescription = stringResource(R.string.screen_splash_logo),
            )
            Text(
                text = stringResource(R.string.screen_splash_advertisement),
                modifier = Modifier.padding(60.dp)
            )
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Composable
fun PreviewThemeLight() {
    MaterialTheme {
        SplashScreen()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewThemeDark() {
    MaterialTheme {
        SplashScreen()
    }
}
