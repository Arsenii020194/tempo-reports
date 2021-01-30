package com.kuteinitsyn.temporeports.rest

import com.kuteinitsyn.temporeports.dto.Filter
import com.kuteinitsyn.temporeports.dto.FilterResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ReportHttp(
    @Value(value = "\${jira.url}")
    var url: String,
    @Value(value = "\${jira.userName}")
    var userName: String,
    @Value(value = "\${jira.password}")
    var password: String,
    val auth: String = Credentials.basic(userName, password)
) {

    private val client = OkHttpClient()


    fun getFilterResponse(filter: Filter): FilterResponse {
        val url = String.format("https://%s/rest/tempo-timesheets/4/worklogs/export/filter", url)
        val request: Request = Request.Builder()
            .url(url)
            .addHeader("Authorization", auth)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(MediaType.parse("application/json"), Json.encodeToString(filter)))
            .build()
        return Json.decodeFromString(
            FilterResponse.serializer(),
            client.newCall(request).execute().body()?.string()!!
        )
    }

    fun getReport(filterKey: String): ByteArray {
        val httpUrl = HttpUrl.parse(
            String.format(
                "https://%s/rest/tempo-timesheets/4/worklogs/export/%s",
                url,
                filterKey
            )
        )
        if (httpUrl != null) {
            val urlBuilder = httpUrl.newBuilder()
            urlBuilder.addQueryParameter("format", "csv")?.addQueryParameter("title", "report")
            val url = urlBuilder.build().url()
            val request: Request = Request.Builder()
                .url(url)
                .addHeader("Authorization", auth)
                .get()
                .build()

            return client.newCall(request).execute().body()?.bytes()!!
        }
        throw RuntimeException("unable to build tempo request url!")
    }
}