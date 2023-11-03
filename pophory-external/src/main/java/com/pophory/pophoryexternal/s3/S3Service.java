package com.pophory.pophoryexternal.s3;


import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pophory.pophoryexternal.s3.AwsS3Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.amazonaws.HttpMethod.*;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AwsS3Config awsS3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    // 파일 업로드
    public void upload(InputStream file, String fileName, ObjectMetadata objectMetadata) {
        awsS3Config.amazonS3Client().putObject(bucket, fileName, file, objectMetadata);
    }

    // 파일 삭제
    public void delete(String fileName) {
        String key = URLDecoder.decode(fileName.split("/")[3], StandardCharsets.UTF_8);
        awsS3Config.amazonS3Client().deleteObject(bucket, key);
    }

    public String getImageUrl(String filePath) {
        return awsS3Config.amazonS3Client().getUrl(bucket, filePath).toString();
    }

    public String getPresignedUrl(String key) {
        return generatePresignedUrlRequest(key).toString();
    }

    private URL generatePresignedUrlRequest(String key) {
        return awsS3Config.amazonS3Client()
                .generatePresignedUrl(new GeneratePresignedUrlRequest(bucket, key)
                        .withMethod(PUT)
                        .withExpiration(getPreSignedUrlExpiration()));
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}