package sample.restore

import java.io.*
import java.util.*

class StateSaver {

    private lateinit var originator : Originator
    private var state : Stack<Memento> = Stack()

    fun save() {
        var fIS : OutputStream? = null
        var oFI : ObjectOutputStream?  = null

        try {
            fIS = FileOutputStream("main.txt")
            oFI = ObjectOutputStream(fIS)


        } catch (e : IOException) {

        } finally {
            fIS?.close()
            oFI?.close()
        }
    }
}