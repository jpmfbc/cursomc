package com.joaopaulo.cursomc.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private Logger logger = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3cliente;

	@Value("${s3.bucket}")
	private String bucketName;

	public void upaloadFile(String localFile) {
		try {
			File file = new File(localFile);
			logger.info("iniciando UPLOAD");
			s3cliente.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			logger.info("UPLOAD finalizado");
		} catch (AmazonServiceException e) {
			logger.info("AmazonServiceException: " + e.getErrorCode());
			logger.info("Status code: " + e.getStatusCode());
		}catch(AmazonClientException e) {
			logger.info("AmazonClientException: " + e.getMessage());
		}
	}
}
