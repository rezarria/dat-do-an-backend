package io.rezarria.food.controller.api.v1

import io.rezarria.food.service.ImageService
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import kotlin.io.path.name

@RestController
@RequestMapping("/api/v1/images")
class ImageController(private val imageService: ImageService) {
    @PostMapping
    fun save(request: ServerHttpRequest): Mono<ResponseEntity<String>> {
        return imageService.save(request.body)
            .map { ResponseEntity.ok().body(it.fileName.name) }
    }

    @PostMapping("/form")
    fun saveForm(@RequestPart("image") file: Mono<FilePart>): Mono<ResponseEntity<String>> {
        return file.flatMap { imageService.save(it.content()) }
            .map { ResponseEntity.ok().body(it.fileName.name) }
    }

    @GetMapping("/{path}", produces = ["image/webp"])
    fun get(@PathVariable path: String): Mono<Resource> {
        return Mono.fromCallable { FileSystemResource(imageService.get(path)) }
    }

    @DeleteMapping("/{path}")
    fun delete(@PathVariable path: String) {
        imageService.delete(path)
    }
}