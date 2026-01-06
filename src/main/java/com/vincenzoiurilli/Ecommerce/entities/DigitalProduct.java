package com.vincenzoiurilli.Ecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;



@Entity
@Table(name="DigitalProducts")
public class DigitalProduct extends Products{

    @Column(nullable = false, unique = true)
    private String fileUrl;

    @Column(nullable = false, unique = true)
    private String fileName;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false)
    private String format;

    public DigitalProduct() {}

    public DigitalProduct(String name, String description, Users seller, float price, int quantity, String fileUrl, String fileName, String fileSize, String format) {
        super(name, description, seller, price, quantity);
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.format = format;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "DigitalProduct{" +
                "fileUrl='" + fileUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
