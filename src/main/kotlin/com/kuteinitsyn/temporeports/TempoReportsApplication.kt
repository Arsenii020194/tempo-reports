package com.kuteinitsyn.temporeports

import com.kuteinitsyn.temporeports.dto.Filter
import com.kuteinitsyn.temporeports.parser.CsvParser
import com.kuteinitsyn.temporeports.rest.ReportHttp
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import kotlin.system.exitProcess


@SpringBootApplication
class TempoReportsApplication(
    var http: ReportHttp,
    var csvParser: CsvParser
) : CommandLineRunner {
    var cliOptions = Options()
    var cliParser = DefaultParser()

    protected fun setupOptions() {
        cliOptions.addOption("p", "project", true, "Project key")
        cliOptions.addOption("o", "output", true, "Output File")
    }

    override fun run(args: Array<String>) {
        setupOptions()
        val cmd = cliParser.parse(cliOptions, args)
        val projectKey: String? = cmd.getOptionValue("p")
        val filePath: String? = cmd.getOptionValue("o")
        val filter = Filter()
        if (projectKey != null) {
            filter.projectKey += projectKey
        }
        val filterResponse = http.getFilterResponse(filter)

        val file = http.getReport(filterResponse.filterKey)
        val tempoText = csvParser.parse(file.inputStream())

        if (filePath != null) {
            val tempoFile = File(filePath)
            if (!tempoFile.exists()) {
                tempoFile.createNewFile()
                tempoFile.writeText(tempoText)
            }
        } else {
            println(tempoText)
        }
    }


}

fun main(args: Array<String>) {
    val ctx = runApplication<TempoReportsApplication>(*args)
    SpringApplication.exit(ctx, ExitCodeGenerator { 0 })
    exitProcess(0)
}

