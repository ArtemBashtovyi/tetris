package sample.restore

import sample.model.figure.BaseFigure

class Originator(var figure: BaseFigure) {

    fun save() : SavedFigure {
        return SavedFigure(figure)
    }

    fun restore(savedFigure : SavedFigure) {
        figure = savedFigure.state
    }
}