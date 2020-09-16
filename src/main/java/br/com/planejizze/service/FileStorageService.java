package br.com.planejizze.service;

import br.com.planejizze.config.FileStorageConfig;
import br.com.planejizze.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploaddir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Erro ao criar diretório!");
        }
    }

    public String storeFile(MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            if (filename.contains("..")) {
                throw new FileStorageException("Arquivo com nome inválido: " + filename);
            }
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Não foi possivel salvar " + filename + " no diretório!");
        }
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("Arquvio com o nome " + filename + " não existe!");
            }
        } catch (Exception e) {
            throw new FileStorageException("Arquvio com o nome " + filename + " não existe!", e);
        }
    }
}
