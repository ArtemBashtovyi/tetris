package sample.network.command

import sample.network.socket.SocketManager

class WriteCommand(override var socketManager: SocketManager) : Command(socketManager) {
    override fun execute() {

    }
}