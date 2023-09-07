package com.itl.pns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.env.Environment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

/**
 * @author shubham.lokhande
 *
 */
@RestController
@RequestMapping("file")
public class FileUploadController implements BaseController{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	@Autowired
	private Environment environment;
	
	@Value("${bot.image.folder}")
	private String botImageFolder;
	
	@RequestMapping(value = "/offerFileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> fileUpload(@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2, HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, String> filepath = new HashMap<>();
		try {

			String response = uploadSmallImage(file1, req);
			if (response != null) {

				response = response.replaceAll("\\\\", "/");
				String arr[] = response.split(":");

				String fullpath = arr[1];
				String[] parts = fullpath.split("/");
				String finalpath = parts[2];
				finalpath = "/" + parts[3] + "/" + parts[4] + "/" + parts[5] + "/" + parts[6] + "/" + parts[7];
				System.out.println(" Fullpath " + finalpath);
				filepath.put(file1.getName(), finalpath);
			} else {
				filepath.put(file1.getName(), "");
			}

			String response2 = uploadBigImage(file2, req);
			if (response2 != null) {
				response2 = response2.replaceAll("\\\\", "/");
				String arr2[] = response2.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file2.getName(), finalpath2);
			} else {
				filepath.put(file2.getName(), "");
			}
		} catch (Exception e) {
			logger.error("You failed upload because the file was empty. %s ", e);
		}
		return new ResponseEntity<>(filepath, HttpStatus.OK);

	}
	
	public String uploadSmallImage(MultipartFile file, HttpServletRequest req) throws IOException {
		System.out.println(" File name" + file.getOriginalFilename());
		String backgroundimage = file.getOriginalFilename();
		FileOutputStream fos = null;
		BufferedOutputStream stream = null;
		String responseUrl = null;
		if (!file.isEmpty()) {
			try {
				String env = String.valueOf(new Date().getTime());
				if (null != environment && !ObjectUtils.isEmpty(environment.getRequiredProperty("env"))) {
					env = environment.getRequiredProperty("env");
				}
				byte[] bytes = file.getBytes();
			
				String imgFilePath = botImageFolder + "/apps/images/" + env + "/OfferUplod";
				File dir = new File(imgFilePath);

				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + backgroundimage);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				responseUrl = serverFile.getAbsolutePath();

			} catch (Exception e) {
				logger.error("You failed to upload " + backgroundimage + " => " + e);
			} finally {
				if (stream != null)
					stream.close();
				if (fos != null)
					fos.close();
			}
		} else {
			logger.info("You failed to upload because the file was empty. %s ", backgroundimage);
		}

		return responseUrl;
	}

	public String uploadBigImage(MultipartFile file, HttpServletRequest req) throws IOException {
		System.out.println(" File name" + file.getOriginalFilename());
		String boticon = file.getOriginalFilename();
		FileOutputStream fos = null;
		BufferedOutputStream stream = null;
		String responseUrl = null;
		if (!file.isEmpty()) {
			try {
				String env = String.valueOf(new Date().getTime());
				if (null != environment && !ObjectUtils.isEmpty(environment.getRequiredProperty("env"))) {
					env = environment.getRequiredProperty("env");
				}
				byte[] bytes = file.getBytes();
				
				String imgFilePath = botImageFolder + "/apps/images/" + env + "/OfferUplod";
				File dir = new File(imgFilePath);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + boticon);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);

				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				responseUrl = serverFile.getAbsolutePath();
				System.out.println("###########" + responseUrl);
			} catch (Exception e) {
				logger.error("You failed to upload " + boticon + " => " + e);
			} finally {
				if (stream != null)
					stream.close();
				if (fos != null)
					fos.close();
			}
		} else {
			logger.info("You failed to upload because the file was empty. %s ", boticon);
		}
		return responseUrl;
	}
	
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> bulkFileUpload(
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			@RequestParam("file3") MultipartFile file3,
			@RequestParam("file4") MultipartFile file4,
			@RequestParam("file5") MultipartFile file5,
			@RequestParam("file6") MultipartFile file6,HttpServletRequest req, HttpServletResponse res) throws IOException {
		Map<String, String> filepath = new HashMap<>();
		try {

		
				
			String response2 = uploadBigImage(file1, req);
			if (response2 != null) {
				response2 = response2.replaceAll("\\\\", "/");
				String arr2[] = response2.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file1.getName(), finalpath2);
			} else {
				filepath.put(file1.getName(), "");
			}
			
			String response3 = uploadBigImage(file2, req);
			if (response3 != null) {
				response3 = response3.replaceAll("\\\\", "/");
				String arr2[] = response3.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file2.getName(), finalpath2);
			} else {
				filepath.put(file2.getName(), "");
			}
			
			String response4 = uploadBigImage(file3, req);
			if (response4 != null) {
				response4 = response4.replaceAll("\\\\", "/");
				String arr2[] = response4.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file3.getName(), finalpath2);
			} else {
				filepath.put(file3.getName(), "");
			}
			
			String response5 = uploadBigImage(file4, req);
			if (response5 != null) {
				response5 = response5.replaceAll("\\\\", "/");
				String arr2[] = response5.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file4.getName(), finalpath2);
			} else {
				filepath.put(file4.getName(), "");
			}
			
			String response6 = uploadBigImage(file5, req);
			if (response6 != null) {
				response6 = response6.replaceAll("\\\\", "/");
				String arr2[] = response6.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file5.getName(), finalpath2);
			} else {
				filepath.put(file5.getName(), "");
			}
			
			String response7 = uploadBigImage(file6, req);
			if (response7 != null) {
				response7 = response7.replaceAll("\\\\", "/");
				String arr2[] = response7.split(":");

				String fullpath2 = arr2[1];
				String[] parts2 = fullpath2.split("/");
				String finalpath2 = parts2[2];
				finalpath2 = "/" + parts2[3] + "/" + parts2[4] + "/" + parts2[5] + "/" + parts2[6] + "/" + parts2[7];
				System.out.println(" Fullpath2 " + finalpath2);

				filepath.put(file6.getName(), finalpath2);
			} else {
				filepath.put(file6.getName(), "");
			}
	
		} catch (Exception e) {
			logger.error("You failed upload because the file was empty. %s ", e);
		}
		return new ResponseEntity<>(filepath, HttpStatus.OK);

	}
	

}
