package tetris.network.socket

import tetris.model.cell.VolatileCell

interface CustomSocket {

    fun write(matrix : ArrayList<VolatileCell>)

    fun close()
}