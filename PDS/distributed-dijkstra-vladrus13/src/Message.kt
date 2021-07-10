package dijkstra.messages

import dijkstra.ProcessImpl
import dijkstra.Type

sealed class Message

data class MessageWithChildren(val type : Type) : Message()

data class MessageRelax(val distance : Long) : Message()