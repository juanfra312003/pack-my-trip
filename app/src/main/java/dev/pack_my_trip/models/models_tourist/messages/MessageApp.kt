package dev.pack_my_trip.models.models_tourist.messages

import java.io.Serializable
import java.util.Date

open class MessageApp (
    var forwarded : Boolean,
    var timeStamp : Date
): Serializable {

    override fun toString(): String {
        return "MessageApp(timeStamp=$timeStamp)"
    }
}