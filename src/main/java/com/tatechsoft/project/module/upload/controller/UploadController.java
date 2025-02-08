package com.tatechsoft.project.module.upload.controller;

import com.tatechsoft.project.common.controller.BaseController;
import com.tatechsoft.project.module.upload.model.UploadFileDto;
import com.tatechsoft.project.module.upload.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UploadFileService uploadFileService;

    public UploadController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadFileDto> upload(@RequestParam("file") MultipartFile file,
                                                @RequestParam("module") String module,
                                                @RequestParam("tableName") String tableName,
                                                @RequestParam("parentId") String parentId) throws IOException {
        return ResponseEntity.ok(uploadFileService.uploadFile(file, module, tableName, parentId));
    }

    @PostMapping(value = "/upload/multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UploadFileDto>> uploadMultipartFile(@RequestParam("files") MultipartFile[] files,
                                                                   @RequestParam("module") String module,
                                                                   @RequestParam("tableName") String tableName,
                                                                   @RequestParam("parentId") String parentId) throws IOException {

        return ResponseEntity.ok(uploadFileService.uploadMultiPartFile(files, module, tableName, parentId));
    }

    @GetMapping("/download/{moduleName}/{fileName}")
    public ResponseEntity<?> download(@PathVariable String moduleName, @PathVariable String fileName) {
        Resource resource = null;
        try {
            resource = uploadFileService.download(moduleName, fileName);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
