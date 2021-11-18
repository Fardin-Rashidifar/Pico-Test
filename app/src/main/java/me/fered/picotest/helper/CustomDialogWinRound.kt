package me.fered.picotest.helper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import me.fered.picotest.R
import me.fered.picotest.databinding.LayoutCustomDialogWinRoundBinding
import me.fered.picotest.ext.SetOnClickCustomDialogWinner


class CustomDialogWinRound(context: Context) : AlertDialog(context), View.OnClickListener {
    private lateinit var setOnClickCustomDialogWinner: SetOnClickCustomDialogWinner
    lateinit var binding: LayoutCustomDialogWinRoundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(
                context
            ), R.layout.layout_custom_dialog_win_round, null, false
        )
        setContentView(binding.root)
        binding.btnNextRound.setOnClickListener(this)
        binding.btnRestartRound.setOnClickListener(this)
    }
    fun setBtnNextVisibility(visible:Boolean){
        if (visible){
            binding.btnNextRound.visibility = View.VISIBLE
        }else{
            binding.btnNextRound.visibility = View.INVISIBLE
        }
    }

    fun setOnClickCustomDialog(onClickCustomDialogWinner: SetOnClickCustomDialogWinner){
        this.setOnClickCustomDialogWinner = onClickCustomDialogWinner
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnNextRound ->{
                this.setOnClickCustomDialogWinner.onNext()
            }
            R.id.btnRestartRound ->{
                this.setOnClickCustomDialogWinner.onRestart()
            }

        }
    }
}