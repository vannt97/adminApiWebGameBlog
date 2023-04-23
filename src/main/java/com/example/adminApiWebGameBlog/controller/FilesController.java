package com.example.adminApiWebGameBlog.controller;

import com.example.adminApiWebGameBlog.entity.FileEntity;
import com.example.adminApiWebGameBlog.message.ResponseFile;
import com.example.adminApiWebGameBlog.message.ResponseMessage;
import com.example.adminApiWebGameBlog.model.FileInfo;
import com.example.adminApiWebGameBlog.payload.reponse.ResponseData;
import com.example.adminApiWebGameBlog.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @PostMapping("files/create-datas")
    public ResponseEntity<?> createDataAuto(){
        return new ResponseEntity<>("Tao thanh cong", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseData responseData = new ResponseData();
        String urlFileImage = storageService.store(file);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(urlFileImage)
                .toUriString();
        responseData.setSucces(true);
        responseData.setData(fileDownloadUri);
        responseData.setStatus(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @GetMapping("/download-all-image-from-server")
    public ResponseEntity<?> downloadAllImage(){
        storageService.downloadAllImageFromServer();
        return ResponseEntity.ok().body("Download thành công");
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFilePNGFromServe", path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @ResponseBody
    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFilePNGFromServe(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



//    @GetMapping("/files")
//    public ResponseEntity<List<ResponseFile>> getListFiles() {
//        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(dbFile.getId())
//                    .toUriString();
//
//            return new ResponseFile(
//                    dbFile.getName(),
//                    fileDownloadUri,
//                    dbFile.getType(),
//                    dbFile.getData().length);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }

//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileEntity fileDB = storageService.getFile(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//                .body(fileDB.getData());
//    }
}
