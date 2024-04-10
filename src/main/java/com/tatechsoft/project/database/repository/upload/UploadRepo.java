package com.tatechsoft.project.database.repository.upload;

import com.tatechsoft.project.database.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadRepo extends JpaRepository<Upload, Long> {

    List<Upload> findByTableParentAndIdParent(String table, String idParent);
}
