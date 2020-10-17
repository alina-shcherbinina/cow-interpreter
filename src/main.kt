import java.io.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun readCow(path: String): String {
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun eval(source: String) {

    val src = ArrayList<String>(source.split(' '))
    val buffer = Array(200) { _ -> (0).toChar() }
    var ptr = 0
    var i = 0

    val blocks = getLoopBlocks(src)
    while (i < src.size) {
        when (src[i]) {

            "moO" -> ptr+= 1
            "mOo" -> ptr-= 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "OOM" -> print(buffer[ptr].toInt())
            "Moo" -> {
                if (buffer[ptr] == (0).toChar()) {
                    print(">>> ")
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                } else {
                    print(buffer[ptr])
                }
            }
            "MOO" -> if (buffer[ptr] == (0).toChar()) {
                i = blocks[i]!!           //начало цикла
            }
            "moo" -> if (buffer[ptr] != (0).toChar()){
                i = blocks[i]!!
            }
        }
        i++
    }
}

fun getLoopBlocks(source: ArrayList<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()) {
        if (char == "MOO") {
            stack.add(i)
        }
        if (char == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}


fun main(args: Array<String>) {
        val source = readCow("hello.cow")
        eval(source)
        println(" Done")
}