import javax.swing.JFrame
import javax.swing.JTextField

class Window {
    var frame = JFrame()

    constructor(){
        frame.setSize(500,500)
        frame.layout = null
        frame.isVisible = true
    }

    fun createNodeStructure(bg:BigNumber){
        for(move in 0..bg.length){
            var node = JTextField(bg.getDigit(false,move).toString())
            node.setBounds(move * 40,10,40,40)
            frame.add(node)
        }

        frame.invalidate()
        frame.validate()
        frame.repaint()
    }
}