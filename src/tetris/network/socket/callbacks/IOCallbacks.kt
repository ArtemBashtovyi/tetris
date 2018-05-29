package tetris.network.socket.callbacks

import tetris.model.cell.VolatileCell

interface IOCallbacks {

    fun onDataReceive(filledCells : ArrayList<VolatileCell>)
}