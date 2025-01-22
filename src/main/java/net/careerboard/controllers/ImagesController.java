package net.careerboard.controllers;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.dto.PresignImageResponse;
import net.careerboard.services.ImagesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImagesController {

    private final ImagesService imagesService;

    @GetMapping("/list-buckets")
    public ResponseEntity<List<String>> listBuckets() {
        List<String> buckets = imagesService.listBuckets();
        return ResponseEntity.ok(buckets);
    }

    @GetMapping("/list-objects")
    public List<String> listObjects() {
        return imagesService.listObjectsInBucket();
    }

    @GetMapping("/upload-url/{imageName}")
    public ResponseEntity<String> generatePresignedUploadUrl(@PathVariable String imageName) {
        try {
            String uploadUrl = imagesService.generatePresignedUploadUrl(imageName);
            return ResponseEntity.ok(uploadUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/view-urls")
    public ResponseEntity<List<PresignImageResponse>> generatePresignedViewUrls(@RequestBody List<String> imageNames) {
        try {
            List<PresignImageResponse> presignImageResponses = imagesService.generatePresignedViewUrls(imageNames);
            return ResponseEntity.ok(presignImageResponses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

