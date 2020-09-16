package br.com.planejizze.resource;

import br.com.planejizze.dto.UploadFIleResponseDTO;
import br.com.planejizze.service.FileStorageService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "File")
@RestController
@RequestMapping(path = "/file")
public class FileResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);
    private final FileStorageService fileStorageService;

    @Autowired
    public FileResource(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(path = "/upload")
    public UploadFIleResponseDTO uploadFIle(@RequestParam("file") MultipartFile multipartFile) {
        String filename = fileStorageService.storeFile(multipartFile);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/file/downloadFile")
                .path(filename).toString();
        return new UploadFIleResponseDTO(filename, fileDownloadUri, multipartFile.getContentType(), multipartFile.getSize());
    }

    @PostMapping(path = "/uploadMultiple")
    public List<UploadFIleResponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files).map(this::uploadFIle).collect(Collectors.toList());
    }

    @GetMapping(path = "/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = "";
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Erro ao baixar uma imagem com nome: " + fileName + ". Erro: " + e.getMessage());
        }
        if (contentType.isEmpty()) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
