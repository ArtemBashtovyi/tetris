package sample.network.command

import sample.network.socket.SocketManager

class CloseCommand(override var socketManager: SocketManager) : Command(socketManager) {
    override fun execute() {

    }
}