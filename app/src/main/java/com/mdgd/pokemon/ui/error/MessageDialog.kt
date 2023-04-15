package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.fragments.HostedDialogFragment
import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.mvi.states.ScreenState

abstract class MessageDialog<
        VIEW : FragmentContract.View,
        STATE : ScreenState<VIEW, STATE>,
        ACTION : AbstractEffect<VIEW>,
        VIEW_MODEL : FragmentContract.ViewModel<VIEW, STATE, ACTION>,
        HOST : FragmentContract.Host>
    : HostedDialogFragment<VIEW, STATE, ACTION, VIEW_MODEL, HOST>() {

    companion object {
        const val KEY_TITLE = "key_title"
        const val KEY_MSG = "key_msg"
        const val KEY_TITLE_STR = "key_title_str"
        const val KEY_MSG_STR = "key_msg_str"
        const val KEY_TYPE = "key_type"
        const val TYPE_INT = 1
        const val TYPE_STR = 2
    }
}
