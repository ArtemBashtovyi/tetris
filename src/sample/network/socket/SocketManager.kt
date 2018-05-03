package sample.network.socket

import sample.model.cell.VolatileCell
import sample.network.socket.callbacks.IOCallbacks

interface SocketManager {

    fun createSocket(ioCallbacks: IOCallbacks)

    fun writeData(filledCells : ArrayList<VolatileCell>)

}