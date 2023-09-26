package com.luminousonion.parser;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MultiPartFileMock implements MultipartFile {
    private String _name;
    private InputStream _inputStream;

    public MultiPartFileMock(String name) {
        this._name = name;
    }
    public MultiPartFileMock(String name, String plainText) {
        this._name = name;
        this._inputStream = new ByteArrayInputStream(plainText.getBytes());
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setupPlainText(String plainText) {
        this._inputStream = new ByteArrayInputStream(plainText.getBytes());
    }


    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this._inputStream;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
