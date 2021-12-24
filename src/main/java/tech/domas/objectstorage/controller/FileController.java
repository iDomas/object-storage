package tech.domas.objectstorage.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.domas.objectstorage.model.FileResponse;
import tech.domas.objectstorage.model.GetFileResponse;
import tech.domas.objectstorage.service.BasicFileHandler;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    private BasicFileHandler fileService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) throws IOException {
        final GetFileResponse getFile = fileService.getFile(fileName);
        InputStream inputStream = getFile.getFis();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(getFile.getMimeType())
                .body(bytes);
    }

    @PostMapping("")
    public ResponseEntity<String> saveFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileExtension") String fileExtension) {
        final FileResponse response = fileService.saveFile(file, fileExtension);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toJSON());
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        final FileResponse response = fileService.deleteFile(fileName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toJSON());
    }

}
