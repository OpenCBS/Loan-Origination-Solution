package com.opencbs.genesis.helpers;

import com.opencbs.genesis.exceptions.ValidationException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Makhsut Islamov on 21.11.2016.
 */
public class FileHelper {
    public static void save(MultipartFile file, Path path) throws ValidationException {
        Assert.notNull(file, "File can not be null");
        Assert.isTrue(!file.isEmpty(), "Failed to store empty file " + file.getOriginalFilename());
        Assert.notNull(path, "Path can not be null");
        try {
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ValidationException("Failed to store file " + file.getOriginalFilename());
        }
    }


    public static void delete(Path path) throws ValidationException {
        Assert.notNull(path, "Path can not be null");
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new ValidationException("Failed to delete file.");
        }
    }


    public static Resource loadAsResource(Path filePath) throws ValidationException {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new ValidationException("Could not read requested file");
        } catch (MalformedURLException e) {
            throw new ValidationException("Could not read requested file");
        }
    }
}