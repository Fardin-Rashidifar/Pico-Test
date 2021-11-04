package me.fered.picotest.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import f.r.geofamilymvvm.R
import f.r.geofamilymvvm.ext.SetDataStatus
import javax.inject.Inject

class CustomDialogWinRound @Inject constructor(){
    private lateinit var dialog: Dialog

    fun showAlertDialog(context: Context,setDataStatus: SetDataStatus,textBtnBottom: String,textTopTv: String,textMiddleTv: String) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog_location_permission)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        val tvMiddle = dialog.findViewById<TextView>(R.id.tvMiddleText)
        val tvTop = dialog.findViewById<TextView>(R.id.tvTopText)
        btnOk.text = textBtnBottom
        tvMiddle.text = textMiddleTv
        tvTop.text = textTopTv
        btnOk.setOnClickListener {
            setDataStatus.isDataCome(true)
            dialog.dismiss()
        }
        dialog.show()
    }
}