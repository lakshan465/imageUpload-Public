package com.lakshan.imagUpload.repo;

import com.lakshan.imagUpload.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<File, Long> {
}
