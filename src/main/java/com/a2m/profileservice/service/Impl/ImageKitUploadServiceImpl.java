package com.a2m.profileservice.service.Impl;

import com.a2m.profileservice.service.ImageKitUploadService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageKitUploadServiceImpl implements ImageKitUploadService {
    private static final String IMAGEKIT_UPLOAD_URL = "https://upload.imagekit.io/api/v1/files/upload";
    private static final String PRIVATE_API_KEY = "private_e2V3fNLKwK0pGwSrEmFH+iKQtks=";
    private static final String PUBLIC_API_KEY = "public_Q+yi7A0O9A+joyXIoqM4TpVqOrQ=";

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // tên file
                }
            });
            body.add("fileName", file.getOriginalFilename());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBasicAuth(PRIVATE_API_KEY, "");

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    IMAGEKIT_UPLOAD_URL,
                    request,
                    String.class
            );

            return response.getBody(); // JSON response
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi đọc file: " + e.getMessage();
        }
    }

    @Override
    public String uploadImages(List<MultipartFile> files) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            StringBuilder responseBuilder = new StringBuilder();

            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                    body.add("file", new ByteArrayResource(file.getBytes()) {
                        @Override
                        public String getFilename() {
                            return file.getOriginalFilename(); // tên file
                        }
                    });
                    body.add("fileName", file.getOriginalFilename());

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    headers.setBasicAuth(PRIVATE_API_KEY, "");

                    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

                    ResponseEntity<String> response = restTemplate.postForEntity(
                            IMAGEKIT_UPLOAD_URL,
                            request,
                            String.class
                    );

                    responseBuilder.append(response.getBody()).append("\n"); // Append each file response
                } else {
                    responseBuilder.append("File is empty or invalid.\n");
                }
            }

            return responseBuilder.toString(); // Return response of each upload
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while uploading files: " + e.getMessage();
        }
    }


}
