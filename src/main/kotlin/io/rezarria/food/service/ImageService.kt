package io.rezarria.food.service

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.io.SequenceInputStream
import java.nio.file.Path
import java.util.*

@Service
class ImageService(private val fileService: FileService) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun save(fileFlux: Flux<DataBuffer>): Mono<Path> {
        val randomName = UUID.randomUUID().toString()
        return fileService.createPath("images", "$randomName.webp")
            .flatMap { fileService.createFile(it) }
            .flatMap { save(fileFlux, it).thenReturn(it) }
    }

    fun get(name: String): Path {
        return fileService.get("images", name)
    }

    fun delete(name: String) {
        fileService.delete("images", name)
    }

    private fun save(fileFlux: Flux<DataBuffer>, path: Path): Mono<Void> {
        return fileFlux.concatMap { Mono.just(it.asInputStream()) }.collectList().flatMap {
            val input = SequenceInputStream(Collections.enumeration(it))
            val image = ImmutableImage.loader().fromStream(input)
            Mono.fromCallable {
                image.output(WebpWriter.DEFAULT,path)
                input.close()
            }.then()
        }
    }
}