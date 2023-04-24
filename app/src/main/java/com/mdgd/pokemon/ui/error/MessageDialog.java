package com.mdgd.pokemon.ui.error;

import com.mdgd.mvi.fragments.FragmentContract;
import com.mdgd.mvi.fragments.HostedDialogFragment;

public abstract class MessageDialog<
        VIEW extends FragmentContract.View,
        VIEW_MODEL extends FragmentContract.ViewModel<VIEW>,
        HOST extends FragmentContract.Host
        > extends HostedDialogFragment<VIEW, VIEW_MODEL, HOST> {

    protected static final String KEY_TITLE = "key_title";
    protected static final String KEY_MSG = "key_msg";
    protected static final String KEY_TITLE_STR = "key_title_str";
    protected static final String KEY_MSG_STR = "key_msg_str";
    protected static final String KEY_TYPE = "key_type";
    protected static final int TYPE_INT = 1;
    protected static final int TYPE_STR = 2;

}
