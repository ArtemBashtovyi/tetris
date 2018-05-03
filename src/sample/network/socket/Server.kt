package sample.network.socket

import sample.model.cell.VolatileCell
import sample.network.socket.callbacks.IOCallbacks
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket

// TODO : replace local host to dynamic IP-address
class Server(private val callback : IOCallbacks, hostIP : String, port : Int) : CustomSocket {

    companion object {
        private const val TAG = "ServerSocket::"
    }

    private var serverSocket : ServerSocket = ServerSocket(port)

    private var clientSocket = serverSocket.accept()

    private var reader  = ObjectInputStream(clientSocket.getInputStream())
    private var writer =  ObjectOutputStream(clientSocket.getOutputStream())


    init {
        serverSocket.soTimeout = 5000
        print("Started!")
        startListening()
    }

    // start listening client in another thread
    private fun startListening() {
        val readerRunnable = Runnable {
            while(true) {
                val enemyMatrix : ArrayList<VolatileCell> = reader.readObject() as ArrayList<VolatileCell>
                callback.onDataReceive(enemyMatrix)
            }
        }
        Thread(readerRunnable).start()
    }


    override fun write(matrix : ArrayList<VolatileCell>) {
        try {
            // use reset for clear cache version of object
            writer.reset()
            writer.writeObject(matrix)
            writer.flush()
        } catch (e : IOException) {
            e.printStackTrace()
        }
    }

    override fun close() {
        try {
            writer.close()
            reader.close()
        } catch (e : IOException) {
            throw IOException("error trying to close")
        }
    }


}