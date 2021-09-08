package me.fered.picotest.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.fered.picotest.R
import me.fered.picotest.dataClass.Picture
import me.fered.picotest.databinding.ItemPictureBinding
import java.util.*


class PictureAdapter(private var pictureList: List<Picture>) : RecyclerView.Adapter<PictureAdapter.CustomHolder>() {

    companion object {
        var LAST_CHOICE: Picture? = null
        var LAST_CHOICE_ITEM_VIEW: AppCompatImageView? = null
        var STOP: Boolean = false
        var SOLVED_COUNT = 0
    }

    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        layoutInflater = LayoutInflater.from(parent.context)

        val itemPictureBinding = DataBindingUtil.inflate<ItemPictureBinding>(layoutInflater,
            R.layout.item_picture, parent, false)

        return CustomHolder(itemPictureBinding, itemPictureBinding.root)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val picture = pictureList[position]
        holder.bind(picture, pictureList)
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun getSolvedCount(): Int {
        return SOLVED_COUNT
    }

    fun setSolvedCount(count: Int) {
        SOLVED_COUNT = count
    }

    class CustomHolder(private val itemPictureBinding: ItemPictureBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(picture: Picture, pictureList: List<Picture>) {
            this.itemPictureBinding.executePendingBindings()

            itemPictureBinding.imageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,
                R.drawable.pattern
            ))

            itemPictureBinding.imageView.setOnClickListener {
                if (!STOP) {
                    itemPictureBinding.imageView.setImageDrawable(picture.drawable)
                    if (!picture.solved) {
                        if (LAST_CHOICE == null) {
                            LAST_CHOICE = picture
                            Log.e("Solved?", "Pick Next")
                            LAST_CHOICE_ITEM_VIEW = itemPictureBinding.imageView
                        } else {
                            if (LAST_CHOICE!!.pair == picture.pair) {
                                if (LAST_CHOICE!!.id != picture.id) {
                                    Log.e("Solved?", "Yes")
                                    picture.solved = true
                                    pictureList[pictureList.indexOf(LAST_CHOICE)].solved = true
                                    SOLVED_COUNT++
                                }
                            } else {
                                Log.e("Solved?", "No")
                                STOP = true

                                Timer().schedule(object : TimerTask() {
                                    override fun run() {
                                        LAST_CHOICE_ITEM_VIEW!!.setImageDrawable(ContextCompat.getDrawable(itemView.context,
                                            R.drawable.pattern
                                        ))
                                        itemPictureBinding.imageView.setImageDrawable(ContextCompat.getDrawable(itemView.context,
                                            R.drawable.pattern
                                        ))
                                        STOP = false
                                    }
                                }, 500)
                            }
                            LAST_CHOICE = null
                        }
                    }
                }
            }
        }

    }


}
