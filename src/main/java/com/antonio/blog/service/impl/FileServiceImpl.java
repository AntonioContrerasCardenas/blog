package com.antonio.blog.service.impl;

import com.antonio.blog.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));

        String filePath = path + File.separator + fileName1;

        File f = new File(filePath);
        if (!f.exists()) {
            f.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);

        return is;
    }
}
