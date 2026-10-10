package com.gehena.vendor_quote_matcher.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "quotes")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne
    @JoinColumn(name="vendor_id" , nullable = false)
    private  Vendor vendor;

    private  String fileName;

    private String fileType;

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;

    private LocalDateTime uploadedAt;

    @PrePersist
    protected  void onCreated(){
        uploadedAt = LocalDateTime.now();

        if(status == null){
            status = QuoteStatus.UPLOADED;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public QuoteStatus getStatus() {
        return status;
    }

    public void setStatus(QuoteStatus status) {
        this.status = status;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
