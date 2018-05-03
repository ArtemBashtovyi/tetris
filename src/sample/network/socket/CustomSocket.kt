package sample.network.socket

import sample.model.cell.VolatileCell

interface CustomSocket {

    fun write(matrix : ArrayList<VolatileCell>)

    fun close()
}