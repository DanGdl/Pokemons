package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.ui.error.state.ErrorFragmentEffect
import com.mdgd.pokemon.ui.error.state.ErrorFragmentState

class ErrorContract {
    interface ViewModel : FragmentContract.ViewModel<ErrorFragmentState, ErrorFragmentEffect>
    interface View : FragmentContract.View
    interface Host : FragmentContract.Host
}
