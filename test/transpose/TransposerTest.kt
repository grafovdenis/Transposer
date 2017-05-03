package transpose

import org.junit.Test

import org.junit.Assert.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileReader
import java.util.stream.Collectors
import java.io.BufferedReader
import java.io.IOException


class TransposerTest {

    @Throws(IOException::class)
    private fun assertFileContent(file: String, expectedContent: String) {
        val bufferedReader = BufferedReader(FileReader(file))
        val content = bufferedReader.lines().collect(Collectors.joining("\n"))
        assertEquals(expectedContent, content)
    }

    @Test
    fun transpose() {
        val t1 = Transposer(0, false, false)
        t1.transpose(FileInputStream("files/input.txt"), FileOutputStream("files/output.txt"))
        assertFileContent("files/output.txt",
                "1 4 6 10\n" +
                "2 5 7 \n" +
                "3   8 \n" +
                "    9 ")
        val t2 = Transposer(10, true, false)
        t2.transpose(FileInputStream("files/input.txt"), FileOutputStream("files/output.txt"))
        assertFileContent("files/output.txt",
                "         1          4          6         10\n" +
                "         2          5          7 \n" +
                "         3                     8 \n" +
                "                               9 ")
        val t3 = Transposer(1, false, true)
        t3.transpose(FileInputStream("files/input.txt"), FileOutputStream("files/output.txt"))
        assertFileContent("files/output.txt",
                "1 4 6 1\n" +
                "2 5 7 \n" +
                "3   8 \n" +
                "    9 ")
    }

}
