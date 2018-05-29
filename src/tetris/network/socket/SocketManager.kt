package tetris.network.socket

import tetris.model.cell.VolatileCell
import tetris.network.socket.callbacks.IOCallbacks

interface SocketManager {

    fun createSocket(ioCallbacks: IOCallbacks)

    fun writeData(filledCells : ArrayList<VolatileCell>)

    fun closeSocket()

}