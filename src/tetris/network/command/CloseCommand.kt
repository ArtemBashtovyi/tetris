package tetris.network.command

import tetris.network.socket.SocketManager

class CloseCommand(override var socketManager: SocketManager) : Command(socketManager) {
    override fun execute() {
        socketManager.closeSocket()
    }
}