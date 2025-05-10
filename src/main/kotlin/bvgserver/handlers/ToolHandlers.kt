package com.example.bvgserver.handlers

import com.example.bvgserver.getLocation
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import kotlinx.serialization.json.*

/**
 * A class to manage tool registrations for the MCP server
 */
class ToolHandlers(
    private val toolRegistry: ToolRegistry
) {
    fun initializeTools() {
        addLocationsTool()
    }
    private fun addLocationsTool() {
        val locationsTool = ServerTool(
            name = "stop_information",
            description = """
                Get stop information from bvg
            """.trimIndent(),
            input = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("stop") {
                        put("type", "string")
                        put("description", "Stop name")
                    }
                }
            ),
            handler = { request ->
                val httpClient = HttpClient {
                    // Install content negotiation plugin for JSON serialization/deserialization
                    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
                }
                val stop = request.arguments["stop"]?.jsonPrimitive?.content
                if (stop == null) {
                    return@ServerTool CallToolResult(
                        content = listOf(TextContent("The 'stop' parameter is required."))
                    )
                }
                val result = httpClient.getLocation(stop)
                CallToolResult(content = listOf(TextContent(result)))
            }

        )
        toolRegistry.registerTool(locationsTool)
    }

}
