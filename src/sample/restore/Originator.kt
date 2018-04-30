package sample.restore

import sample.model.figure.BaseFigure

class Originator(var figure: BaseFigure) {

    fun save() : Memento {
        return Memento(figure)
    }

    fun restore(memento : Memento) {
        figure = memento.state
    }
}