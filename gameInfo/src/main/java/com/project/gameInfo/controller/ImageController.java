package com.project.gameInfo.controller;

import com.project.gameInfo.controller.dto.ImageDto;
import com.project.gameInfo.domain.Image;
import com.project.gameInfo.service.GamesService;
import com.project.gameInfo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    @Value("${custom.resource-path}")
    private String IMAGE_PATH;


    @GetMapping("/all/image/{url}")
    public ResponseEntity<?> getImage(@PathVariable("url") String url) throws IOException {

        try {
            InputStream inputStream = new FileInputStream(IMAGE_PATH + url);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            inputStream.transferTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            inputStream.close();

            return ResponseEntity.ok(bytes);

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이미지를 찾지 못하였습니다.");
        }

    }

    @PostMapping("/user/image")
    public ResponseEntity<?> saveImages(@RequestPart(value = "file") MultipartFile file) throws IOException {

        Image image = uploadImage(file);

        if (image != null) {
            Long save = imageService.save(image);

            return ResponseEntity.ok(ImageDto.builder()
                    .id(image.getId())
                    .originName(image.getOriginName())
                    .url(image.getUrl()).build()
            );

        } else{

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지 파일이 아닙니다.");
        }
    }

    public Image uploadImage(MultipartFile file) throws IOException{

        String uuid = UUID.randomUUID().toString();
        String url = IMAGE_PATH + "\\" + uuid + file.getOriginalFilename();

        if(file.getContentType().contains("image") || file.getSize() > 0){
            try {
                File upload = new File(url);
                file.transferTo(upload);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Image(file.getOriginalFilename(), uuid + file.getOriginalFilename());

        } else {
            return null;
        }
    }



}
