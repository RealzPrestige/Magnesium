package dev.zprestige.magnesium.manager

import java.io.*

open class FileManager {

    inner class FileObject(val file: File) {

        fun createWriter(): Writer {
            return Writer(this)
        }


        fun createReader(): Reader {
            return Reader(this)
        }


        fun createDir(): FileObject {
            if (!file.exists()) {
                file.mkdirs()
            }
            return this
        }

        fun createFile(): FileObject {
            if (!file.exists()) {
                file.createNewFile()
            }
            return this
        }

        inner class Writer(private val fileObject: FileObject) {
            private val bufferedWriter = BufferedWriter(FileWriter(fileObject.file))

            fun write(string: String): Writer {
                bufferedWriter.write(string + "\n")
                return this
            }

            fun close(): FileObject {
                bufferedWriter.close()
                return fileObject
            }
        }

        inner class Reader(private val fileObject: FileObject) {
            private val bufferedReader = BufferedReader(InputStreamReader(DataInputStream(FileInputStream(fileObject.file))))


            fun lines(): List<String> {
                return bufferedReader.readLines()
            }

            fun close(): FileObject {
                bufferedReader.close()
                return fileObject
            }
        }
    }
}