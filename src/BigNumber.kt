class BigNumber() {
    var isPositive = true
    var length = 0
    private var head:BigNode? = null
    private var tail:BigNode? = null

    constructor(number:String) : this() {
        if(number.startsWith('-')) isPositive = false

        var digits = number.replace("^-?0+","")
        length = digits.length

        /* Set up each digit to a BigNode */
        for (i in 0..digits.length-1){
            var node = BigNode(
                digits[i].toString().toInt(),
                null,
                null
            )

            // If first node, set up as head and tail
            if(i == 0){
                head = node
                tail = node
            }
        }
    }

    fun display(){
        if(head == null){
            println(0)
        }
        else {
            println(head?.value)
        }
    }
}

private class BigNode(var value:Int,private var prev:BigNode?, private var next:BigNode?){

    fun travese(nodesFromStart:Int){

    }
}