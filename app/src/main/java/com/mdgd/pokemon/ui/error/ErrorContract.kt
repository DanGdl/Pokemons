package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.FragmentContract

class ErrorContract {
    interface ViewModel : FragmentContract.ViewModel<ErrorFragmentState>
    interface View : FragmentContract.View
    interface Host : FragmentContract.Host
}
