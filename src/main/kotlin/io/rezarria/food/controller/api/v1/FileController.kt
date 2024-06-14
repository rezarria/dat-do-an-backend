package io.rezarria.food.controller.api.v1

import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/v1/file")
class FileController {
    @PostMapping("/upload")
    fun upload(@RequestPart("files") filePartFlux: Flux<FilePart>) {

    }
}