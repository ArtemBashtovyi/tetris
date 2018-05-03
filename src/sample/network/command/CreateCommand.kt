package sample.network.command

import sample.network.socket.SocketManager

class CreateCommand(override var socketManager: SocketManager) : Command(socketManager)  {
    override fun execute() {

    }
}