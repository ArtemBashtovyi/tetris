package sample.network.socket

import sample.model.cell.VolatileCell
import sample.network.socket.callbacks.IOCallbacks
import java.io.*
import java.net.Socket

class Client(private val callback : IOCallbacks, serverName: String, port : Int) : CustomSocket {

    companion object {
        private const val TAG = "ClientSocket::"
    }

    private var socket : Socket = Socket(serverName,port)
    private var writer  = ObjectOutputStream(socket.getOutputStream())
    private var reader  = ObjectInputStream(socket.getInputStream())

    init {
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
            writer.reset()
            writer.writeObject(matrix)
            writer.flush()
        } catch (e: IOException) {
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