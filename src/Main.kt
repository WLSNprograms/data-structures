fun main(){
    var bg1 = BigNumber("-85671")
    var bg2 = BigNumber("-85171")
    var bg3 = BigNumber("1032")
    var bg4 = BigNumber("-1032")
    var bg5 = BigNumber()

    bg5.addDigit(1,true)
    bg5.concatenate(0)
    bg5.concatenate(2)
    bg5.concatenate(0)
    bg5.concatenate(7)
    bg5.addDigit(2,true)
    println(bg5)
}