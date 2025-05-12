# BVG MCP Server

An unofficial Model Context Protocol (MCP) server for interacting with BVG api.

## Server Versions

There is currently only 1 version of the BVG MCP server available:

### 1. STDIO Transport Server 

- Uses standard input/output (STDIO) for communication
- Designed for integrations with Claude Desktop and other MCP clients that support STDIO
- Each client session spawns a new server process
- The server terminates when the client disconnects

**Usage with Claude Desktop:**
1. Configure `claude_desktop_config.json` to point to this server
2. Open Claude Desktop and select the  tool

## Overview

This server implements the Model Context Protocol (MCP) specification to provide AI assistant models with access to BVG Api

### Available MCP Tools

1. Stop Information : It provides the information for a location in berlin. Eg: Turmstrasse. It returns the stop name for now. Since this is a starter project.
