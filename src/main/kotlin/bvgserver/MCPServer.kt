package com.example.bvgserver

import com.example.bvgserver.handlers.ToolHandlers
import com.example.bvgserver.handlers.ToolRegistry
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import io.modelcontextprotocol.kotlin.sdk.*
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import kotlinx.serialization.json.*

fun main() {
    System.err.println("Starting MCP server...")
    val server: Server = createServer()
    val stdioServerTransport = StdioServerTransport(
        System.`in`.asSource().buffered(),
        System.out.asSink().buffered()
    )
    runBlocking {
        System.err.println("Setting up server connection...")
        val job = Job()
        server.onCloseCallback = { 
            System.err.println("Server close callback triggered")
            job.complete() 
        }
        try {
            System.err.println("Connecting server to transport...")
            server.connect(stdioServerTransport)
            System.err.println("Server connected successfully")
            job.join()
            System.err.println("Job completed")
        } catch (e: Exception) {
            System.err.println("Error in server connection: ${e.message}")
            e.printStackTrace(System.err)
        }
    }
    System.err.println("Server process exiting")
}

fun createServer(): Server {
    val info = Implementation(
        "BVG MCP Server",
        "1.0.0"
    )
    val options = ServerOptions(
        capabilities = ServerCapabilities(tools = ServerCapabilities.Tools(true))
    )
    val server = Server(info, options)

    // Use the ToolRegistry to register all tools
    val registry = ToolRegistry()
    ToolHandlers(registry).initializeTools()
    registry.initializeTools(server)

    return server
}

suspend fun HttpClient.getLocation(stop: String) : String {
    val resp = this.get("https://v6.bvg.transport.rest/locations?poi=false&addresses=false&query=$stop").body<JsonArray>()
    return resp.first().jsonObject["name"]?.jsonPrimitive?.content ?: "No location found"
}
