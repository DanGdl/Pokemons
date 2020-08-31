package com.mdgd.pokemon.ui.error;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.mdgd.pokemon.R;

public class ErrorFragment extends MessageDialog<ErrorFragmentState, ErrorContract.ViewModel, ErrorContract.Host>
        implements ErrorContract.View, DialogInterface.OnClickListener {

    private Throwable error;

    public static ErrorFragment newInstance(String title, String message) {
        final Bundle b = new Bundle();
        b.putString(KEY_TITLE_STR, title);
        b.putString(KEY_MSG_STR, message);
        b.putInt(KEY_TYPE, TYPE_STR);
        final ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setArguments(b);
        return errorFragment;
    }

    public static ErrorFragment newInstance(int title, int message) {
        final Bundle b = new Bundle();
        b.putInt(KEY_TITLE, title);
        b.putInt(KEY_MSG, message);
        b.putInt(KEY_TYPE, TYPE_INT);
        final ErrorFragment errorFragment = new ErrorFragment();
        errorFragment.setArguments(b);
        return errorFragment;
    }

    @Override
    protected ErrorContract.ViewModel createModel() {
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bundle args = getArguments();
        // todo add trace printing
        if (args != null) {
            final int type = args.getInt(KEY_TYPE);
            if (TYPE_INT == type) {
                final int titleResId = args.getInt(KEY_TITLE, R.string.empty);
                if (titleResId != 0) {
                    builder.setTitle(titleResId);
                }
                final int messageResId = args.getInt(KEY_MSG, R.string.empty);
                if (messageResId != 0) {
                    final String message = getString(messageResId) + (error == null ? "" : " " + error.getMessage());
                    builder.setMessage(message);
                }
            } else if (TYPE_STR == type) {
                builder.setTitle(args.getString(KEY_TITLE_STR, ""));
                final String message = args.getString(KEY_MSG_STR, "") + (error == null ? "" : " " + error.getMessage());
                builder.setMessage(message);
            }
        }
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE: {
                dismissAllowingStateLoss();
            }
            break;
        }
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
