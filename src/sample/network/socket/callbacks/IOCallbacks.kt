package sample.network.socket.callbacks

import sample.model.cell.VolatileCell

interface IOCallbacks {

    fun onDataReceive(matrix : ArrayList<VolatileCell>)
}