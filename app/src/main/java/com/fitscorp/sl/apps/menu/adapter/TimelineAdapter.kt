package com.fitscorp.sl.apps.menu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.common.inflate
import com.fitscorp.sl.apps.menu.data.DataArr
import kotlinx.android.synthetic.main.timeline_main_cell.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout








const val PAYMENT_OPTIONS = 1


class TimelineAdapter(val context: Context, val list: ArrayList<DataArr>) :
    RecyclerView.Adapter<TimelineAdapter.BaseHolder>() {



    fun release() {
        notifyDataSetChanged()
    }
    var onitemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
   //     fun onPaymentProcess(obj: Timeline)
    //    fun onProcessAction(action: String,meta: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        when (viewType) {
            PAYMENT_OPTIONS -> {
                return MainProgressView(parent)
            }
            else -> {
                return SubProgressView(parent)
            }
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return PAYMENT_OPTIONS
    }

    open inner class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: Any) = with(itemView) {
        }
        open fun release() {
        }
    }

    inner class MainProgressView(parent: ViewGroup) :

        BaseHolder(parent.inflate(com.fitscorp.sl.apps.R.layout.timeline_main_cell)) {

        @SuppressLint("SetTextI18n")
        override fun bind(item: Any) = with(itemView) {

           val data= item as DataArr

            txt_title.text=data.shortName
            txt_dsc.text="Your "+data.longName
            txt_dsc_persentage.text=data.value1+"%"

            txt_descbellow.text="Target "+data.longName
            txt_dsc_persentage3.text=data.value2.toString()+"%"

            val colo1:String=data.color1
            progress_bar.progressColor = Color.parseColor(colo1)
            progress_bar.radius=10
            if(data.value1.toFloat()<10){
                progress_bar.progress= 10.0F
            }else{
                progress_bar.progress= data.value1.toFloat()
            }

            if(data.value2.toFloat()<10){
                progress_bar2.progress= 10.0F
            }else{
                progress_bar2.progress= data.value2.toFloat()
            }


            val colo2:String=data.color2
            progress_bar2.progressColor = Color.parseColor(colo2)
            progress_bar2.radius=10




            return@with
        }
    }
    inner class SubProgressView(parent: ViewGroup) :

        BaseHolder(parent.inflate(R.layout.timeline_sub_cell)) {

        override fun bind(item: Any) = with(itemView) {


            return@with
        }
    }





}
