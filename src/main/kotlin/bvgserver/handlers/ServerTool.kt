package com.example.bvgserver.handlers

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Tool

data class ServerTool(
    val name: String,
    val description: String,
    val input: Tool.Input,
    val handler: suspend (CallToolRequest) -> CallToolResult
)
