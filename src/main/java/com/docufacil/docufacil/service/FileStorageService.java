package com.docufacil.docufacil.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {
        try {

            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de almacenamiento", e);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("No se puede guardar un archivo vacío.");
            }

            String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFileName)).toAbsolutePath();

            file.transferTo(destinationFile.toFile());

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo en el disco duro", e);
        }
    }

    public byte[] loadFile(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo físico", e);
        }
    }
}