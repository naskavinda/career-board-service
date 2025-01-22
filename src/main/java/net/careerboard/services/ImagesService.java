package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.dto.PresignImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    @Value("${aws.access-key}")
    private String accessKey;
    @Value("${aws.secret-key}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Value("${aws.s3.presign-duration-minutes}")
    private int presignDurationMinutes;

    public List<String> listBuckets() {
        try {
            ListBucketsResponse bucketsResponse = s3Client.listBuckets();
            return bucketsResponse.buckets().stream()
                    .map(Bucket::name)
                    .collect(Collectors.toList());
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return List.of();
    }

    public String uploadFileContent(String bucketName, String key, String content) {
        try {
            // Create a PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // Upload the content
            s3Client.putObject(putObjectRequest, RequestBody.fromString(content));

            return "File content uploaded successfully to bucket: " + bucketName + " with key: " + key;
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to upload file content: " + e.awsErrorDetails().errorMessage(), e);
        }
    }

    public List<String> listObjectsInBucket() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public List<PresignImageResponse> generatePresignedUploadUrl(List<String> imageNames) {
        return imageNames.stream()
                .map(imageName -> {
                    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(imageName)
                            .build();

                    PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(presignDurationMinutes))
                            .putObjectRequest(putObjectRequest)
                            .build();

                    URL presignedUrl = s3Presigner.presignPutObject(presignRequest).url();
                    s3Presigner.close();

                    return new PresignImageResponse(presignedUrl.toString(), imageName);
                })
                .toList();
    }

    private String generatePresignedViewUrl(String imageName) {
        GetObjectRequest putObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(imageName)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(presignDurationMinutes))
                .getObjectRequest(putObjectRequest)
                .build();

        URL presignedUrl = s3Presigner.presignGetObject(presignRequest).url();
        s3Presigner.close();

        return presignedUrl.toString();
    }

    public List<PresignImageResponse> generatePresignedViewUrls(List<String> imageNames) {
        return imageNames.stream()
                .map(imageName -> new PresignImageResponse(generatePresignedViewUrl(imageName), imageName))
                .toList();
    }
}
