package com.example.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class UploadToS3Service {
	
	@Value("#{environment.ACCESS_KEY}")
	String accessKey;
	@Value("#{environment.SECRET_KEY}")
	String secretKey;

public String uploadImageToS3Image(MultipartFile image) {
		
		String imageLink = "";
		BasicAWSCredentials credObj = new BasicAWSCredentials(accessKey,
				secretKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credObj))
				.withRegion(Regions.US_EAST_2).build();

		try {
			PutObjectRequest putRequest = new PutObjectRequest("uploadimageassignmentbucket",
					image.getOriginalFilename(), image.getInputStream(), new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead);

			s3client.putObject(putRequest);

			 imageLink = "http://" + "uploadimageassignmentbucket" + ".s3.amazonaws.com/"
					+ image.getOriginalFilename();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		return imageLink;
	}
	
	
	public String uploadImageToS3(String image,FileInputStream fs) {
		
		String imageLink = "";
		BasicAWSCredentials credObj = new BasicAWSCredentials(accessKey,
				secretKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credObj))
				.withRegion(Regions.US_EAST_2).build();
		/*String uniqueID = UUID.randomUUID().toString();
		uniqueID = uniqueID.replaceAll("-", "_");*/

		try {
			PutObjectRequest putRequest = new PutObjectRequest("uploadimageassignmentbucket",
					image, fs, new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead);

			s3client.putObject(putRequest);
			
			
			 imageLink = "http://" + "uploadimageassignmentbucket" + ".s3.amazonaws.com/"
					+ image;
			 
			// System.out.println("Unique id in imageLink:"+ uniqueID);
			 System.out.println("Unique id in imageLink:"+ imageLink);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		return imageLink;
	}
	
	public String uploadRecording(String name, FileInputStream fs) {
		String fileLink = "";
		BasicAWSCredentials credObj = new BasicAWSCredentials(accessKey,
				secretKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credObj))
				.withRegion(Regions.US_EAST_2).build();

		try {
			PutObjectRequest putRequest = new PutObjectRequest("uploadimageassignmentbucket",
					name, fs, new ObjectMetadata())
							.withCannedAcl(CannedAccessControlList.PublicRead);

			s3client.putObject(putRequest);

			 fileLink = "http://" + "uploadimageassignmentbucket" + ".s3.amazonaws.com/"+name;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return fileLink;
	}
}
