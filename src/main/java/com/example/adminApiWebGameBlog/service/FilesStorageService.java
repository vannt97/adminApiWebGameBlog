package com.example.adminApiWebGameBlog.service;

import com.example.adminApiWebGameBlog.entity.FileEntity;
import com.example.adminApiWebGameBlog.expections.FileCanntUploadException;
import com.example.adminApiWebGameBlog.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilesStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

//    @Value("${upload.path}")
//    private String fileUpload;

    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    public void downloadAllImageFromServer(){
        List<FileEntity> list =  fileDBRepository.findAll();
        list.forEach(file -> {
            try {
                Files.copy(new ByteArrayInputStream(file.getData()), this.root.resolve(Objects.requireNonNull(file.getName())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        }catch (Exception e){
            throw new FileCanntUploadException("File cannt upload!!");
        }
    }

    @Transactional
    public String store(MultipartFile file) throws IOException {
            Path pathFileName = this.root.resolve(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            String typeFile = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String nameFile = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
            int num = 0;
            while (Files.exists(pathFileName)){
                pathFileName = this.root.resolve(StringUtils.cleanPath(nameFile + "_" + num + typeFile));
                num++;
            }

            File convFile = new File(String.valueOf(pathFileName));
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            FileEntity FileDB = new FileEntity(pathFileName.getFileName().toString(), file.getContentType(), file.getBytes());
            fileDBRepository.save(FileDB);
            return pathFileName.getFileName().toString();




//        Path pathFileName = this.root.resolve(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
//        String typeFile = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        String nameFile = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
//
//
//        int num = 0;
//        String resultPath = "";
//        while (Files.exists(pathFileName)){
//            pathFileName = this.root.resolve(StringUtils.cleanPath(nameFile + "_" + num + typeFile));
//            num++;
//        }
//        FileEntity FileDB;
//        try {
//            System.out.println(pathFileName);
//            System.out.println(this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
//            Files.copy(file.getInputStream(), pathFileName);
//            FileDB = new FileEntity(pathFileName.getFileName().toString(), file.getContentType(), file.getBytes());
//        }catch (Exception e){
//            if (e instanceof FileAlreadyExistsException) {
//                throw new RuntimeException("A file of that name already exists.");
//            }
//            throw new RuntimeException(e.getMessage());
//        }
//        fileDBRepository.save(FileDB);

    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }



    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public FileEntity getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
