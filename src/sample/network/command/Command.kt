package sample.network.command

import sample.network.socket.SocketManager

abstract class Command(open var socketManager : SocketManager) {
    abstract fun execute()
}