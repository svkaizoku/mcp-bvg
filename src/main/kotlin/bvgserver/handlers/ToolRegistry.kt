package com.example.bvgserver.handlers

import io.modelcontextprotocol.kotlin.sdk.server.Server

class ToolRegistry(
) {
   companion object {
       private val tools: MutableList<ServerTool> = mutableListOf()
   }

    fun initializeTools(server: Server) {
        tools.forEach { tool ->
            server.addTool(tool.name, tool.description, tool.input, tool.handler)
        }
    }

    fun registerTool(tool: ServerTool) {
        tools.add(tool)
    }


}