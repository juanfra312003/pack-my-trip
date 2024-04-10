package dev.pack_my_trip.adapters.tourist_adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dev.pack_my_trip.R
import dev.pack_my_trip.models.models_tourist.messages.MessageApp
import dev.pack_my_trip.models.models_tourist.messages.TextMessage

class MessageAdapter(context: Context, messages: List<MessageApp>)
    : ArrayAdapter<MessageApp>(context, 0, messages) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false)
        }

        val msg : TextView
        val img : ImageView
        val frameForwarded = itemView!!.findViewById<FrameLayout>(R.id.frameForwarded)
        val frameReceived = itemView.findViewById<FrameLayout>(R.id.frameReceived)


        if(item!!.forwarded){
            msg = itemView.findViewById(R.id.messageForwarded)
            img = itemView.findViewById(R.id.messageImgForwarded)

            val paramsframe = LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsframe.weight = 3.0f
            frameForwarded.layoutParams = paramsframe
            frameReceived.removeAllViews()
        }
        else {
            img = itemView.findViewById(R.id.messageImgReceived)
            msg = itemView.findViewById(R.id.messageReceived)
            val paramsframe = LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            paramsframe.weight = 3.0f
            frameReceived.layoutParams = paramsframe
            frameForwarded.removeAllViews()
        }

        when(item){
            is TextMessage -> {
                msg.text = item.text
            }
        }
        return itemView
    }
}