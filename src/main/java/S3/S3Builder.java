package S3;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

public class S3Builder {
    static S3Client s3Client;
    Region region;

    public S3Builder(){
        region = Region.US_EAST_1;
        s3Client = S3Client.builder()
                .region(region)
                .build();
    }

    public void listBuckets(){
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach((x)-> System.out.println(x.name()));
    }

    public void uploadObjectsToBucket(String bucketName, File file, String keyName){
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .acl(ObjectCannedACL.PUBLIC_READ)
                .bucket(bucketName)
                .key(keyName)
                .build();
        s3Client.putObject(objectRequest, RequestBody.fromFile(file));

    }

    public void deleteObject(String bucketName,String keyName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    public ResponseInputStream<GetObjectResponse> downloadObject(String bucketName, String keyName){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        return s3Client.getObject(getObjectRequest);
    }

    public static void listBucketObjects(String bucketName ) {

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
            .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> objects = res.contents();

            for (ListIterator iterVals = objects.listIterator(); iterVals.hasNext(); ) {
                System.out.println("    // S3  Object \\\\");
                S3Object myValue = (S3Object) iterVals.next();
                System.out.println("File Name: "+myValue.key());
                System.out.println("Owner:  "+myValue.owner());
                System.out.println("Tag: "+myValue.eTag());
                System.out.println("Last Modified: "+myValue.lastModified());
//                System.out.println("Other: "+myValue.sdkFields());
                System.out.println("    // End  \\\\");

            }

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
