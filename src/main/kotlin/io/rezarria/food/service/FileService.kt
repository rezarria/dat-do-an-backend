package io.rezarria.food.service

import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Path


private const val UPLOADS = "D:\\upload"

@Service
class FileService {

    fun createPath(vararg names: String): Mono<Path> {
        return Mono.fromCallable {
            if (names.size > 1) {
                val paths = names.dropLast(1).toTypedArray()
                val directory = Path.of(UPLOADS, *paths)
                if (!Files.exists(directory)) {
                    Files.createDirectories(directory)
                }
            }
            return@fromCallable Path.of(UPLOADS, *names)
        }
    }

    fun createFile(filePath: Path): Mono<Path> {
        return Mono.fromCallable {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath)
            }
            filePath
        }
    }

    private fun writeBytesToFile(byteFlux: Flux<DataBuffer>, filePath: Path): Flux<DataBuffer> {
        val writer = Files.newBufferedWriter(filePath)
        return byteFlux
            .doOnNext {
                writer.write(it.readableByteBuffers().toString())
            }
            .doOnError {
                writer.close()
                Files.delete(filePath)
            }
            .doFinally { writer.close() }
    }

    fun save(fileFlux: Flux<DataBuffer>, vararg paths: String): Mono<Path> {
        return createPath(*paths).flatMap(this::createFile)
            .flatMap { writeBytesToFile(fileFlux, it).then().thenReturn(it) }
    }

    fun get(vararg names: String): Path = Path.of(UPLOADS, *names)

    fun delete(vararg names: String) {
        val path = Path.of(UPLOADS, *names)
        if (Files.exists(path))
            Files.delete(path)
    }
}