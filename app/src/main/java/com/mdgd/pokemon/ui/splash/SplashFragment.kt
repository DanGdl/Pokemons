package com.mdgd.pokemon.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mdgd.mvi.fragments.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.bg.UploadWorker
import com.mdgd.pokemon.ui.splash.state.SplashScreenAction
import com.mdgd.pokemon.ui.splash.state.SplashScreenState

class SplashFragment : HostedFragment<SplashContract.View, SplashScreenState, SplashScreenAction, SplashContract.ViewModel, SplashContract.Host>(), SplashContract.View {

    override fun createModel(): SplashContract.ViewModel {
        return ViewModelProvider(this, SplashViewModelFactory(PokemonsApp.instance?.appComponent!!)).get(SplashViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun proceedToNextScreen() {
        fragmentHost?.proceedToPokemonsScreen()
    }

    override fun launchWorker() {
        fragmentHost?.let {
            val uploadWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
            WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest)
        }
    }

    override fun showError(error: Throwable?) {
        fragmentHost?.showError(error)
    }
}
