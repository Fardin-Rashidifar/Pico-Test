package me.fered.picotest.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.fered.picotest.R
import me.fered.picotest.dataClass.Picture
import me.fered.picotest.databinding.ActivityGameBinding
import me.fered.picotest.ext.SetOnClickCustomDialogWinner
import me.fered.picotest.helper.CustomDialogWinRound
import me.fered.picotest.view.adapter.PictureAdapter

class AnimalActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityGameBinding
    private lateinit var pictureAdapter: PictureAdapter
    private lateinit var pictureRecyclerView: RecyclerView
    private var pictureListShuffled: MutableList<Picture> = ArrayList()
    private var pictureList: MutableList<Picture> = ArrayList()
    private var backgroundLevelStep: Int = BACKGROUND_MAX_LEVEL / (TIME * 20)
    lateinit var customDialogWinRound: CustomDialogWinRound
    lateinit var runnable: Runnable
    lateinit var handler: Handler

    companion object {
        private  var TIME: Int = 30
        private const val BACKGROUND_MAX_LEVEL = 10000
        private var isSolved: Boolean = false
        private var whichStep: Int = 0
        private var allCards: Int = 8
        private var active = false

        fun startGameActivity(context: Context) {
            val intent = Intent(context, AnimalActivity::class.java)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateStatusBarColorTransparent()
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        initGame()
        startGame()

        activityMainBinding.showCardButton.setOnClickListener {
            var firstPic: Picture? = null
            var secondPic: Picture? = null
            for (i in 0 until pictureListShuffled.size) {
                if (!pictureListShuffled[i].solved) {
                    firstPic = pictureListShuffled[i]
                    secondPic = if (firstPic.id!! % 2 == 0) {
                        pictureList[(firstPic.id!! - 1) - 1]
                    } else {
                        pictureList[(firstPic.id!! + 1) - 1]
                    }
                    break
                }
            }

            if (firstPic != null && secondPic != null) {
                pictureRecyclerView[pictureListShuffled.indexOf(firstPic)].performClick()
                pictureRecyclerView[pictureListShuffled.indexOf(secondPic)].performClick()
            } else {
                it.isEnabled = false
            }

        }

        activityMainBinding.moreTimeButton.setOnClickListener {
            activityMainBinding.parentLayout.background.level = 0
        }

        activityMainBinding.restartGameButton.setOnClickListener { restartGame() }

    }

    private fun initGame() {
        pictureRecyclerView = activityMainBinding.pictureRecyclerView
        pictureAdapter = PictureAdapter(pictureListShuffled,this)
        pictureRecyclerView.layoutManager = GridLayoutManager(this, 4)
        pictureRecyclerView.adapter = pictureAdapter

        pictureList.add(Picture(1, ContextCompat.getDrawable(this, R.drawable.girafa), 1, false))
        pictureList.add(Picture(2, ContextCompat.getDrawable(this, R.drawable.girafa), 1, false))
        pictureList.add(Picture(3, ContextCompat.getDrawable(this, R.drawable.leao), 2, false))
        pictureList.add(Picture(4, ContextCompat.getDrawable(this, R.drawable.leao), 2, false))
        pictureList.add(Picture(5, ContextCompat.getDrawable(this, R.drawable.macaco), 3, false))
        pictureList.add(Picture(6, ContextCompat.getDrawable(this, R.drawable.macaco), 3, false))
        pictureList.add(Picture(7, ContextCompat.getDrawable(this, R.drawable.pato), 4, false))
        pictureList.add(Picture(8, ContextCompat.getDrawable(this, R.drawable.pato), 4, false))
        pictureList.add(Picture(9, ContextCompat.getDrawable(this, R.drawable.tigre), 5, false))
        pictureList.add(Picture(10, ContextCompat.getDrawable(this, R.drawable.tigre), 5, false))
        pictureList.add(Picture(11, ContextCompat.getDrawable(this, R.drawable.touro), 6, false))
        pictureList.add(Picture(12, ContextCompat.getDrawable(this, R.drawable.touro), 6, false))
        pictureList.add(Picture(13, ContextCompat.getDrawable(this, R.drawable.gato), 7, false))
        pictureList.add(Picture(14, ContextCompat.getDrawable(this, R.drawable.gato), 7, false))
        pictureList.add(Picture(15, ContextCompat.getDrawable(this, R.drawable.rato), 8, false))
        pictureList.add(Picture(16, ContextCompat.getDrawable(this, R.drawable.rato), 8, false))
        if (whichStep >= 1) {
            pictureList.add(
                Picture(
                    17,
                    ContextCompat.getDrawable(this, R.drawable.cartoon_monkey),
                    9,
                    false
                )
            )
            pictureList.add(
                Picture(
                    18,
                    ContextCompat.getDrawable(this, R.drawable.cartoon_monkey),
                    9,
                    false
                )
            )

            pictureList.add(
                Picture(
                    19,
                    ContextCompat.getDrawable(this, R.drawable.monkey),
                    10,
                    false
                )
            )
            pictureList.add(
                Picture(
                    20,
                    ContextCompat.getDrawable(this, R.drawable.monkey),
                    10,
                    false
                )
            )

        }
        if (whichStep >= 2) {
            pictureList.add(
                Picture(
                    21,
                    ContextCompat.getDrawable(this, R.drawable.cat),
                    11,
                    false
                )
            )
            pictureList.add(
                Picture(
                    22,
                    ContextCompat.getDrawable(this, R.drawable.cat),
                    11,
                    false
                )
            )

            pictureList.add(
                Picture(
                    23,
                    ContextCompat.getDrawable(this, R.drawable.elephant),
                    12,
                    false
                )
            )
            pictureList.add(
                Picture(
                    24,
                    ContextCompat.getDrawable(this, R.drawable.elephant),
                    12,
                    false
                )
            )

        }

        if (whichStep >= 3) {
            pictureList.add(
                Picture(
                    25,
                    ContextCompat.getDrawable(this, R.drawable.owl),
                    13,
                    false
                )
            )
            pictureList.add(
                Picture(
                    26,
                    ContextCompat.getDrawable(this, R.drawable.owl),
                    13,
                    false
                )
            )

            pictureList.add(
                Picture(
                    27,
                    ContextCompat.getDrawable(this, R.drawable.bear),
                    14,
                    false
                )
            )
            pictureList.add(
                Picture(
                    28,
                    ContextCompat.getDrawable(this, R.drawable.bear),
                    14,
                    false
                )
            )

        }

        pictureListShuffled.addAll(pictureList)
        pictureListShuffled.shuffle()

        pictureAdapter.notifyDataSetChanged()

        activityMainBinding.parentLayout.background.level = 0
        activityMainBinding.timeTextView.text = TIME.toString()
    }

    private fun startGame() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                if (!isSolved) {
                    when {
                        pictureAdapter.getSolvedCount() == allCards -> {
                            finishGame(1)
                            isSolved = true
                        }
                        activityMainBinding.parentLayout.background.level >= BACKGROUND_MAX_LEVEL -> {
                            finishGame(0)
                        }
                        else -> {
                            activityMainBinding.parentLayout.background.level += backgroundLevelStep
                            activityMainBinding.timeTextView.text =
                                (TIME - (activityMainBinding.parentLayout.background.level / (BACKGROUND_MAX_LEVEL / TIME))).toString()
                        }
                    }
                }

                handler.postDelayed(this, 50)
            }
        }
        handler.post(runnable)
    }

    private fun finishGame(status: Int) {
        when (status) {
            1 -> {
                Log.d("omade", "finishGame: ")
                // activityMainBinding.timeTextView.text = "تبریک شما برنده شدید"
                customDialogWinRound = CustomDialogWinRound(this)
                if (active){
                    customDialogWinRound.show()
                }
                customDialogWinRound.setCancelable(false)
                customDialogWinRound.setCanceledOnTouchOutside(false)
                if (whichStep + 1 == 4){
                    customDialogWinRound.setBtnNextVisibility(false)
                }else{
                    customDialogWinRound.setBtnNextVisibility(true)
                }
                customDialogWinRound.setOnClickCustomDialog(object : SetOnClickCustomDialogWinner {
                    override fun onRestart() {
                        allCards = 8
                        whichStep = 0
                        TIME = 30
                        restartGame()
                        customDialogWinRound.dismiss()
                    }

                    override fun onNext() {
                        allCards += 2
                        whichStep += 1
                        TIME += 10
                        restartGame()
                        customDialogWinRound.dismiss()
                    }
                })
            }
            0 -> {
                activityMainBinding.timeTextView.text = "متاسفانه وقت شما به پایان رسید"
            }
        }
        activityMainBinding.moreTimeButton.isEnabled = false
        activityMainBinding.showCardButton.isEnabled = false
        for (i in 0 until pictureRecyclerView.size) {
            pictureRecyclerView[i].isEnabled = false
        }

//        handler.removeCallbacksAndMessages(null)
        activityMainBinding.restartGameButton.visibility = VISIBLE
    }

    private fun restartGame() {
        isSolved = false
        activityMainBinding.parentLayout.background.level = 0
        pictureAdapter.setSolvedCount(0)
        pictureList.clear()
        pictureListShuffled.clear()
        for (i in 0 until pictureRecyclerView.size) {
            pictureRecyclerView[i].isEnabled = true
        }
        activityMainBinding.restartGameButton.visibility = INVISIBLE
        activityMainBinding.moreTimeButton.isEnabled = true
        activityMainBinding.showCardButton.isEnabled = true
        initGame()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun updateStatusBarColorTransparent() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun onStart() {
        super.onStart()
        active = true
    }

    override fun onStop() {
        super.onStop()
        active = false
    }

}