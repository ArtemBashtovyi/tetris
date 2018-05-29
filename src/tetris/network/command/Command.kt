package tetris.network.command

import tetris.network.socket.SocketManager

abstract class Command(open var socketManager : SocketManager) {
    abstract fun execute()
}