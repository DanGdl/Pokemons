package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.fragments.FragmentContract

class ErrorContract {
    interface ViewModel : FragmentContract.ViewModel<View>
    interface View : FragmentContract.View
    interface Host : FragmentContract.Host
}
