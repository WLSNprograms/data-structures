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
            // TODO: set next node
            node.next = tail!!.traverse(false,i-1)
            head = tail?.traverse(false,i)
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

    fun addNumber(other:BigNumber):BigNumber{
        // Start at head for both

        // Add both digits together

        // If greater than 9 then you must store the least significant digit and add the higher significant digit
        // to the next node. i.e. 9 + 9 = 18 -> this node = 8 && add + 1 to next sum

        // Continue until both are null

        var thisCurrent = this.head
        var otherCurrent = other.head

        var currentSum = 0
        var carryOver = 0

        // Issue with numbers being different sizes
        // If they aren't the same sizes then the loop stops when either becomes null
        while(thisCurrent != null || otherCurrent != null){
            // I was using += instead of just = for the initial assignment so the previous sum was carrying over
            currentSum = if (thisCurrent?.value != null) thisCurrent.value else 0
            currentSum += if (otherCurrent?.value != null) otherCurrent.value else 0
            currentSum += carryOver

            if(currentSum > 9){
                thisCurrent?.value = currentSum.toString()[1].toString().toInt()
                carryOver =currentSum.toString()[0].toString().toInt()
                if(thisCurrent === this.tail){
                    addDigit(carryOver,true)
                }
            } else {
                thisCurrent?.value = currentSum
                carryOver = 0
            }

            thisCurrent = thisCurrent?.next
            otherCurrent = otherCurrent?.next
        }
        return this
    }

    //Only adds digits, not the signs
    fun concatenate(digit:Int){
        var numbers = digit.toString().replace("\\^-?0+","").replace("-","")
        numbers.map{addDigit(it.toString().toInt(),false)}
    }

    fun addDigit(digit:Int,toTail:Boolean = true){
        if(toTail){
            if(this.tail != null){
                if (this.head === this.tail) {
                    if (this.tail?.value == 0) {
                        if(digit > 0 && digit < 10){
                            head?.value = digit
                            tail?.value = digit
                            length += 1
                        }
                    } else {
                        if (digit >= 0 && digit < 10) {
                            var newNode = BigNode(digit)
                            newNode.prev = tail
                            tail?.next = newNode
                            tail = newNode
                            length+=1
                        }
                    }
                } else {
                    if (digit >= 0 && digit < 10) {
                        var newNode = BigNode(digit)
                        newNode.prev = tail
                        tail?.next = newNode
                        tail = newNode
                        length+=1
                    }
                }
            } else {
                if(digit > 0 && digit < 10){
                    var newNode = BigNode(digit)
                    head = newNode
                    tail = newNode
                    length+=1
                }
            }
        } else {
            if(this.head != null) {
                if (this.head === this.tail) {
                    if (this.head?.value == 0) {
                        // Head is equal to tail and value is 0 means this is the first digit to be added
                        if(digit > 0 && digit < 10){
                            head?.value = digit
                            tail?.value = digit
                            length += 1
                        }
                    } else {
                        if (digit >= 0 && digit < 10) {
                            var newNode = BigNode(digit)
                            newNode.next = head
                            head?.prev = newNode
                            head = newNode
                            length+=1
                        }
                    }
                } else {
                    if (digit >= 0 && digit < 10) {
                        var newNode = BigNode(digit)
                        newNode.next = head
                        head?.prev = newNode
                        head = newNode
                        length+=1
                    }
                }
            } else {
                if(digit > 0 && digit < 10){
                    var newNode = BigNode(digit)
                    head = newNode
                    tail = newNode
                    length+=1
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