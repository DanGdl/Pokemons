package com.mdgd.pokemon.ui.error;

import com.mdgd.pokemon.ui.arch.FragmentContract;
import com.mdgd.pokemon.ui.arch.HostedDialogFragment;
import com.mdgd.pokemon.ui.arch.ScreenState;

public abstract class MessageDialog<STATE extends ScreenState, VIEW_MODEL extends FragmentContract.ViewModel<STATE>, HOST extends FragmentContract.Host>
        extends HostedDialogFragment<STATE, VIEW_MODEL, HOST> {

    protected static final String KEY_TITLE = "key_title";
    protected static final String KEY_MSG = "key_msg";
    protected static final String KEY_TITLE_STR = "key_title_str";
    protected static final String KEY_MSG_STR = "key_msg_str";
    protected static final String KEY_TYPE = "key_type";
    protected static final int TYPE_INT = 1;
    protected static final int TYPE_STR = 2;

}
