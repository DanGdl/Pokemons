package com.mdgd.pokemon.ui.error

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.mdgd.pokemon.R

class ErrorFragment : MessageDialog<
        ErrorContract.View,
        ErrorContract.ViewModel,
        ErrorContract.Host>(),
    ErrorContract.View, DialogInterface.OnClickListener {

    private var error: Throwable? = null

    override fun createModel(): ErrorContract.ViewModel? {
        return null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val args = arguments
        // todo add trace printing, add retry
        if (args != null) {
            val type = args.getInt(KEY_TYPE)
            if (TYPE_INT == type) {
                val titleResId = args.getInt(KEY_TITLE, R.string.empty)
                if (titleResId != 0) {
                    builder.setTitle(titleResId)
                }
                val messageResId = args.getInt(KEY_MSG, R.string.empty)
                if (messageResId != 0) {
                    val message =
                        getString(messageResId) + if (error == null) "" else " " + error!!.message
                    builder.setMessage(message)
                }
            } else if (TYPE_STR == type) {
                builder.setTitle(args.getString(KEY_TITLE_STR, ""))
                val message = args.getString(
                    KEY_MSG_STR,
                    ""
                ) + if (error == null) "" else " " + error!!.message
                builder.setMessage(message)
            }
        }
        builder.setPositiveButton(android.R.string.ok, this)
        return builder.create()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                dismissAllowingStateLoss()
            }
        }
    }

    fun setError(error: Throwable?) {
        this.error = error
    }

    companion object {

        fun newInstance(title: String?, message: String?): ErrorFragment {
            val b = Bundle()
            b.putString(KEY_TITLE_STR, title)
            b.putString(KEY_MSG_STR, message)
            b.putInt(KEY_TYPE, TYPE_STR)
            val errorFragment = ErrorFragment()
            errorFragment.arguments = b
            return errorFragment
        }

        fun newInstance(title: Int, message: Int): ErrorFragment {
            val b = Bundle()
            b.putInt(KEY_TITLE, title)
            b.putInt(KEY_MSG, message)
            b.putInt(KEY_TYPE, TYPE_INT)
            val errorFragment = ErrorFragment()
            errorFragment.arguments = b
            return errorFragment
        }
    }
}
