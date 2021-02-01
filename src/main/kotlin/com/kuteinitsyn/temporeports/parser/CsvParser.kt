package com.kuteinitsyn.temporeports.parser

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class CsvParser {

    @Value(value = "\${tempo.pattern}")
    lateinit var pattern: String

    fun parse(file: InputStream): String {
        var parsedPattern = pattern
        var result = ""
        csvReader().open(file) {
            readAllWithHeaderAsSequence().forEach { row: Map<String, String> ->
                parsedPattern = pattern
                row.forEach { (key, value) ->
                    if (pattern.contains(key)) {
                        parsedPattern = parsedPattern.replace(key, value)
                    }
                }
                result += "$parsedPattern\n"
            }
        }
        return result
    }
}