package com.example.ecommerc_training.fragments.dialog

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.ecommerc_training.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setBottomSheetDialog(
    onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)

    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    //Views

    val emailEditText = view.findViewById<EditText>(R.id.resetEmail_Ed)
    val sendButton = view.findViewById<Button>(R.id.send_Btn)
    val cancelButton = view.findViewById<Button>(R.id.cancel_Btn)


    sendButton.setOnClickListener {
        val email = emailEditText.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()

    }
    cancelButton.setOnClickListener {
        dialog.dismiss()
    }
}