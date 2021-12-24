package tech.domas.objectstorage.service;

import org.springframework.web.multipart.MultipartFile;
import tech.domas.objectstorage.model.FileResponse;
import tech.domas.objectstorage.model.GetFileResponse;

public interface FileService {

    public GetFileResponse getFile(String fileName);

    public FileResponse saveFile(MultipartFile file, String fileExtension);

    public FileResponse deleteFile(String fileName);

}
