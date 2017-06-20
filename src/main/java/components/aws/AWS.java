package components.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import exceptions.IncorrectOperationException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class AWS {

    private final String bucketName;
    private final String keyName;
    private final String accessKey;
    private final String secretKey;

    @Setter
    private AWSCredentials credentials;

    public AWS(String bucketName, String keyName, String accessKey, String secretKey) {
        this.bucketName = bucketName;
        this.keyName = keyName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @PostConstruct
    public void initCredentials() {
        credentials = new BasicAWSCredentials(accessKey, secretKey);
    }


    private void uploadFile(File file) {
        AmazonS3 s3client = new AmazonS3Client(credentials);
        try {
            log.info("Uploading a new object to S3 from a file...\n");
            s3client.putObject(new PutObjectRequest(
                    bucketName, keyName, file));
            log.info("Done)))))\n");

        } catch (AmazonServiceException ase) {
            logAmazonServiceException(ase);
        } catch (AmazonClientException ace) {
            logAmazonClientException(ace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFile() {
        AmazonS3 s3client = new AmazonS3Client(credentials);
        try {
            System.out.println("Deleting object from S3...\n");
            s3client.deleteObject(new DeleteObjectRequest(bucketName, "i.jpg"));
            System.out.println("Done)\n");

        } catch (AmazonClientException ace) {
            logAmazonClientException(ace);
        }
    }

    private String downloadFile() {
        AmazonS3 s3client = new AmazonS3Client(credentials);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            log.info("Downloading object from S3...\n");
            S3Object s3Object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            log.info("Done)\n");
            log.info("Content-Type: " + s3Object.getObjectMetadata().getContentType());

            S3ObjectInputStream is = s3Object.getObjectContent();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String currentLine;
            try {
                while ((currentLine = reader.readLine()) != null) {
                    stringBuilder.append(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (AmazonClientException ace) {
            logAmazonClientException(ace);
        }
        log.info(stringBuilder.toString());
        return stringBuilder.toString();

    }

    private void logAmazonServiceException(AmazonServiceException ase) {
        log.info("Caught an AmazonServiceException, which means your request made it " +
                "to Amazon S3, but was rejected with an error response for some reason.");
        log.info("Error Message:    {} " + ase.getMessage());
        log.info("HTTP Status Code: {} " + ase.getStatusCode());
        log.info("AWS Error Code:   {} " + ase.getErrorCode());
        log.info("Error Type:       {} " + ase.getErrorType());
        log.info("Request ID:       {} " + ase.getRequestId());
    }

    private void logAmazonClientException(AmazonClientException ace) {
        log.info("Caught an AmazonClientException, which " +
                "means the client encountered " +
                "an internal error while trying to " +
                "communicate with S3, " +
                "such as not being able to access the network.");
        log.info("Error Message: " + ace.getMessage());
    }

    public void executeUpdate(String operation, File file) {
        switch (operation) {
            case "upload":
                uploadFile(file);
                break;
            case "delete":
                deleteFile();
                break;
            default:
                break;
        }
    }

    public String executeRead(String operation) {
        if (operation.equals("download")) {
            return downloadFile();
        } else {
            throw new IncorrectOperationException("Incorrect operation name for reading data");
        }
    }
}
