package org.example.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout


fun Application.configureWebsockets(){
    install(WebSockets){
        pingPeriod = java.time.Duration.ofSeconds(15)
        timeout = java.time.Duration.ofSeconds(30)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}