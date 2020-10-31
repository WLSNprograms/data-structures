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

        // Set up each digit to a BigNode

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

    override fun toString(): String {
        var s = ""
        if(head == null){
            s += 0
        }
        else {
            if(!isPositive){
                s += "-"
            }
            for (i in 0..length - 1){
                s += tail!!.traverse(false,i).value
            }
        }

        return s
    }


    fun compareTo(other:BigNumber):Int{
        // Check if either number is negative
        when{
            this.isPositive && !other.isPositive -> return 1
            !this.isPositive && other.isPositive -> return -1
            !this.isPositive && !other.isPositive ->{
                // Both are negative so find the number further from 0
                val comparison = compareValues(this.length,other.length)
                when(comparison){
                    1 -> return -1
                    -1 -> return 1
                    else -> {
                        for (i in 0..this.length-1){
                            val c = compareValues(this.tail?.traverse(false,i)?.value,
                            other.tail?.traverse(false,i)?.value)

                            if(c != 0){
                                // Should I multiple by -1 because I want the smaller one?
                                return c * -1
                            }
                        }
                        return 0
                    }
                }
            }
            else -> {
                // Both are positive so find the number closer to 0
                val comparison = compareValues(this.length,other.length)
                when(comparison){
                    1 -> return 1
                    -1 -> return -1
                    else -> {
                        for (i in 0..this.length-1){
                            val c = compareValues(this.tail?.traverse(false,i)?.value,
                                other.tail?.traverse(false,i)?.value)

                            if(c != 0){
                                // Should I multiple by -1 because I want the smaller one?
                                return c
                            }
                        }
                        return 0
                    }
                }
            }
        }
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