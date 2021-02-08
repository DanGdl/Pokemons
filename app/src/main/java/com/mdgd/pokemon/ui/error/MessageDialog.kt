package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.FragmentContract
import com.mdgd.mvi.HostedDialogFragment
import com.mdgd.mvi.ScreenState

abstract class MessageDialog<STATE : ScreenState<*>, VIEW_MODEL : FragmentContract.ViewModel<STATE>, HOST : FragmentContract.Host>
    : HostedDialogFragment<STATE, VIEW_MODEL, HOST>() {

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