package br.com.planejizze.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFIleResponseDTO implements Serializable {

    private static final long serialVersionUID = 578137532236511172L;

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
