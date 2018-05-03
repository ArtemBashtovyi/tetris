package sample.ui.menu

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import sample.network.socket.SocketManager
import sample.network.socket.SocketManagerImpl
import sample.network.socket.SocketType
import sample.ui.OnKeyListener
import sample.ui.UiConstants
import sample.ui.field.FieldView

class MenuView {

    @FXML
    internal var layout: AnchorPane? = null
    private lateinit var onKeyListener : OnKeyListener
    private lateinit var socketManager : SocketManager

    @FXML
    fun initialize() {
        layout?.maxWidth = UiConstants.MenuField.FIELD_WIDTH.toDouble()
        layout?.maxHeight = UiConstants.MenuField.FIELD_HEIGHT.toDouble()

    }

    @Throws(Exception::class)
    private fun showField(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/res/field.fxml"))

        val root = loader.load<Parent>()
        // set key listener

        val controller : FieldView = loader.getController()

        onKeyListener = controller
        controller.init(socketManager)

        val scene = Scene(root, UiConstants.MAIN_SCREEN_WIDTH.toDouble(), UiConstants.MAIN_SCREEN_HEIGHT.toDouble())

        scene.setOnKeyPressed { event ->
            when (event.code) {
                KeyCode.UP -> onKeyListener.onUp()
                KeyCode.LEFT -> onKeyListener.onLeft()
                KeyCode.RIGHT -> onKeyListener.onRight()
                KeyCode.DOWN -> onKeyListener.onDown()
                else -> { }
            }
        }

        primaryStage.title = "Hello World"
        primaryStage.scene = scene
        primaryStage.show()
    }


    @FXML
    private fun onShowButtonClick() {
        val stage : Stage = layout!!.scene!!.window as Stage
        showField(stage)
    }

    @FXML
    private fun onServerButtonClick() {
        socketManager  = SocketManagerImpl.getInstance(SocketType.SERVER)
    }

    @FXML
    private fun onClientButtonClick() {
        socketManager = SocketManagerImpl.getInstance(SocketType.CLIENT)
    }


}