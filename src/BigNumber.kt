import java.util.regex.Pattern

class BigNumber() {
    var isPositive = true
    var length = 0
    private var head:BigNode? = null
    private var tail:BigNode? = null

    constructor(number:String) : this() {
        if(number.startsWith('-')) isPositive = false

        var digits = number.replace("\\^-?0+","")
        digits = digits.replace("-","")
        length = digits.length

        //println("Length is ${length}")
        /* Set up each digit to a BigNode */

        for (i in 0..digits.length-1){
            var node = BigNode(digits[i].toString().toInt())

            // If first node, set up as head and tail
            if(i == 0){
                head = node
                tail = node
                continue
            }

            tail!!.traverse(false,i-1).prev = node
            head = tail?.traverse(false,i-1)
        }
    }

    fun display(){
        if(head == null){
            println(0)
        }
        else {
            if(!isPositive){
                print("-")
            }
            for (i in 0..length - 1){
                print(tail!!.traverse(false,i).value)
            }
        }
    }

    fun compareTo(other:BigNumber):Int{
        if(this.length > other.length){
            return 1
        } else if (this.length < other.length){
            return -1
        } else {
            // They are the same length
        }

        return 0
    }
}

private class BigNode(var value:Int){
    var prev:BigNode? = null
    var next:BigNode? = null

    fun traverse(forward:Boolean, nodesFromStart:Int):BigNode{
        var node:BigNode = this

        /*TODO: Add checks to make sure they aren't going outside the range
        * Right now it loops and only assigns the next/prev node if it isn't null.
        * */
        for (n in 1..nodesFromStart){
            if(forward) {
                node = if (node.next != null) node.next!! else node
            } else {
                node = if (node.prev != null) node.prev!! else node
            }
        }

        return node
    }
}