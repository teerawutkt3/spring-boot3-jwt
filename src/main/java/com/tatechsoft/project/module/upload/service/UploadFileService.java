package com.tatechsoft.project.module.upload.service;

import com.tatechsoft.project.common.exception.DataNotFoundException;
import com.tatechsoft.project.common.utils.DateUtils;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import com.tatechsoft.project.database.entity.Upload;
import com.tatechsoft.project.database.repository.upload.UploadRepo;
import com.tatechsoft.project.module.upload.model.FileUploadDto;
import com.tatechsoft.project.module.upload.model.UploadFileDto;
import com.tatechsoft.project.module.upload.exception.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UploadFileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${defaultUploadPath}")
    private String dir;

    @Autowired
    private UploadRepo uploadRepo;

    public List<UploadFileDto> uploadMultiPartFile(MultipartFile[] files, String module, String table, String parentId) throws IOException {
        logger.info("uploadMultipartFile[] module: {} table: {} profileBenefitId: {}", module, table, parentId);
        List<UploadFileDto> voList = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFileDto vo = uploadFile(file, module, table, parentId);
            voList.add(vo);
        }
        return voList;
    }

    public UploadFileDto uploadFile(MultipartFile file, String module) throws IOException {
        return this.uploadFile(file, module, null, null);
    }

    public UploadFileDto uploadFile(MultipartFile file, String module, String table, String parentId) throws IOException {

        logger.info("uploadFile: module: {} table: {} parentId: {}", module, table, parentId);
        UploadFileDto vo = new UploadFileDto();

        String path = dir + "/" + module + "/";
        File convert = new File(path);
        if (!convert.exists() && convert.mkdirs()) {
            logger.info("creating directory: {}", convert.getName());
            boolean result = false;
            try {
                convert.mkdirs();
                result = true;
            } catch (SecurityException se) {
                logger.info("Not DIR created : {}", convert.getName());
            }
            if (result) {
                logger.info("DIR created");
            }
        }

        String fileName = file.getOriginalFilename();
        String[] splitFileName = fileName.split("\\.");
        fileName = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDDHHMMSS)
                + "-"
                + UUID.randomUUID().toString()
                + "." + splitFileName[splitFileName.length - 1];

        path += fileName;
        convert = new File(path);
        convert.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convert);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        vo.setPath(path);
        vo.setName(fileName);
        vo.setSize(file.getSize());
        vo.setType(file.getContentType());
        logger.info("Upload file success");

        // save log upload
        var upload = new Upload();
        upload.setModuleName(module);
        upload.setFilename(vo.getName());
        upload.setSize(Double.valueOf(vo.getSize()));
        upload.setPath(vo.getPath());
        upload.setCreatedBy(UserLoginUtils.getUsername());
        upload.setTableParent(table);
        upload.setIdParent(parentId);
        upload.setDisplayName(file.getOriginalFilename());
        logger.info("save upload req: {}", upload);
        uploadRepo.save(upload);
        logger.info("save upload success");

        return vo;
    }

    public boolean isFile(String module, String fileName) {
        String filePath = dir + "/" + module + "/" + fileName;
        File f = new File(filePath);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    public Resource download(String moduleName, String fileName) {
        try {
            Path root = Paths.get(dir + "/" + moduleName);
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(FileUploadDto req) {
        Long uploadId = req.getUploadId();
        Optional<Upload> uploadOpt = uploadRepo.findById(uploadId);
        if (uploadOpt.isEmpty()) throw new DataNotFoundException("ไม่พบข้อมูลไฟล์");
        var upload = uploadOpt.get();
        uploadRepo.deleteById(uploadId);
        logger.info("database delete file by upload: {} success", uploadId);
        File f = new File(upload.getPath());
        logger.info("delete file from directory...");
        if (f.delete()) {
            logger.info("delete file from directory success");
        } else {
            logger.error("can not delete file from directory");
        }
    }
}
