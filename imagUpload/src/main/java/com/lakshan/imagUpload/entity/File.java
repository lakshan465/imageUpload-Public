package com.lakshan.imagUpload.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String fileUrl;
}
