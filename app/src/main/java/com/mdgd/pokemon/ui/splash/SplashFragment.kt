package com.mdgd.pokemon.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mdgd.mvi.HostedFragment
import com.mdgd.pokemon.PokemonsApp
import com.mdgd.pokemon.R
import com.mdgd.pokemon.bg.UploadWorker

class SplashFragment : HostedFragment<SplashContract.View, SplashScreenState, SplashContract.ViewModel, SplashContract.Host>(), SplashContract.View, SplashContract.Router {

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    override fun createModel(): SplashContract.ViewModel {
        return ViewModelProvider(this, SplashViewModelFactory(PokemonsApp.instance?.appComponent!!, this)).get(SplashViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun proceedToNextScreen() {
        if (hasHost()) {
            fragmentHost!!.proceedToPokemonsScreen()
        }
    }

    override fun launchWorker() {
        if (hasHost()) {
            val uploadWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).build()
            WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest)
        }
    }

    override fun showError(error: Throwable?) {
        if (hasHost()) {
            fragmentHost!!.showError(error)
        }
    }

//    override fun onChanged(screenState: SplashScreenState) {
//        screenState.visit(this)
//    }
}
