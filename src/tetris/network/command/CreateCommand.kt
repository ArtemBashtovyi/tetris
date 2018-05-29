package tetris.network.command

import tetris.network.socket.SocketManager
import tetris.network.socket.callbacks.IOCallbacks

class CreateCommand(override var socketManager: SocketManager, private val ioCallbacks: IOCallbacks) : Command(socketManager)  {
    override fun execute() {
        socketManager.createSocket(ioCallbacks)
    }
}