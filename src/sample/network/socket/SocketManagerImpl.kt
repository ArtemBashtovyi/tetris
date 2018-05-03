package sample.network.socket

import javafx.scene.control.Cell
import sample.model.cell.VolatileCell
import sample.network.socket.callbacks.IOCallbacks

class SocketManagerImpl private constructor(private val socketType: SocketType) : SocketManager {

    companion object {
        private const val PORT = 6868
        private const val SERVER_NAME = "localhost"
        @Volatile private var INSTANCE: SocketManagerImpl? = null

        fun getInstance(socketType: SocketType): SocketManagerImpl =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: SocketManagerImpl(socketType).also {
                        INSTANCE = it
                        println("SocketManager :: create()")
                    }
                }
    }


    lateinit var socket : CustomSocket

    override fun createSocket(ioCallbacks: IOCallbacks) {
        socket = if (socketType == SocketType.CLIENT) Client(ioCallbacks, SERVER_NAME, PORT) else
            Server(ioCallbacks,"null", PORT)
    }


    // conversion matrix to list of filled cells
    override fun writeData(filledCells: ArrayList<VolatileCell>) {
        socket.write(filledCells)
    }

    fun closeSockets() {
        socket.close()
    }

}
