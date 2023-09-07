package com.itl.pns.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PdfGenerator {

	@Value("${PSB_LOGO_IMAGE_PATH}")
	private String psbImagePath;
	
	@Value("${PSB_WATERMARK_IMAGE_PATH}")
	private String psbWatermarkPath;

//	public static void main(String[] args) {
//		PdfGenerator pdfGenerator = new PdfGenerator();
//		pdfGenerator.psbImagePath = args[0];
//		String fileName = "UserCredentials.pdf";
//		String Subject = "PSB: User Credentials";
//		Map<String, String> map = new HashMap<>();
//		map.put("User Name", "mj");
//		map.put("Password", "=hwvdBKX2Q");
//		List<Map<String, String>> record = Arrays.asList(map);
//		String ownerpassword = "AAAAA1111A1111";
//		String userPassWord = "AAAAA1111A1111";
//		String a[] = new String[] { "Infrasoft", "9926222815", "NPCI", "mj", "83738373", "hwvdBKX2Q", "Admin" };
//		List<String> genRec = Arrays.asList(a);
//		String pdfFilePath = args[1];
//		pdfGenerator.generatePDF(fileName, Subject, record, ownerpassword, userPassWord, genRec, pdfFilePath);
//
//	}

	public File generatePDF(String fileName, String Subject, List<Map<String, String>> record, String ownerpassword,
			String userPassWord, List<String> genRec, String pdfFilePath) {
		File file = null;
		try {

			PDDocument document = new PDDocument();
			file = new File(pdfFilePath + File.separator + fileName);
			PDPage firstPage = new PDPage();
			document.addPage(firstPage);
			PDFont font = PDType1Font.TIMES_ROMAN;
			float fontSize = 12;
			float fontHeight = fontSize;
			float leading = 11;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
			Date date = new Date();
			String name = genRec.get(0);
			String mobile = genRec.get(1);
			String corpName = genRec.get(2);
			String username = genRec.get(3);
			String corpId = genRec.get(4);
			String temporaryPass = genRec.get(5);
			String userRole = genRec.get(6);
			String ownerpass = ownerpassword;
			String userPass = userPassWord;
			int keyLength = 128;
			/************************ Table ***********************/
			float yCordinate = firstPage.getCropBox().getUpperRightY() - 50;
			float startX = firstPage.getCropBox().getLowerLeftX() + 50;
			float endX = firstPage.getCropBox().getUpperRightX() - 50;
			int pageHeight = (int) firstPage.getTrimBox().getHeight();
			int pageWidth = (int) firstPage.getTrimBox().getWidth();
			PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
			contentStream.setFont(font, fontSize);
			System.out.println("psbImage.."+psbImagePath);
			PDImageXObject image1 = PDImageXObject.createFromFile(psbImagePath, document);
			contentStream.drawImage(image1, 50, pageHeight - 90, 165, 50);

			contentStream.beginText();
			contentStream.newLineAtOffset(startX, yCordinate);
			contentStream.showText("");
			yCordinate -= fontHeight; // This line is to track the yCordinate
			contentStream.newLineAtOffset(0, -leading);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -leading);
			yCordinate -= leading;
			contentStream.newLineAtOffset(220, -leading);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Registered Office: 21 Rajendra Place, New Delhi-110008");
			yCordinate -= fontHeight;
			contentStream.newLineAtOffset(0, 5);
			yCordinate -= leading;
			contentStream.endText();

			contentStream.moveTo(startX, yCordinate);
			contentStream.lineTo(endX, yCordinate);
			contentStream.stroke();
			yCordinate -= leading;

			contentStream.beginText();
			contentStream.newLineAtOffset(startX, yCordinate);
			contentStream.newLineAtOffset(0, 5);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -leading);
			yCordinate -= leading;
			contentStream.newLineAtOffset(220, -leading);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
			contentStream.showText("PSB UnIC Biz");
			yCordinate -= fontHeight;
			contentStream.newLineAtOffset(0, -leading);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -leading);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
			contentStream.showText("Registration Kit");
			yCordinate -= leading;
			contentStream.newLineAtOffset(180, -leading);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
			contentStream.showText(dateFormat.format(date));
			contentStream.endText();

			contentStream.beginText();
			contentStream.newLineAtOffset(startX, yCordinate);
			contentStream.newLineAtOffset(0, -5);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Name of the user: Mr./Mrs./Ms. " + name);
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Mobile Number: " + mobile);
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Corporate Name: " + corpName);
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("Dear Sir/Madam,");
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"  Congratulations! You have been successfully registered on PSB UnIC Biz. We welcome you to a");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"whole new world of banking services available at your ease. Your registration details are");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("mentioned below:");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Corporate ID: " + corpId);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Username: " + username);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("Temporary Password: " + temporaryPass);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.showText("User Role: " + userRole);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"We request you to complete your online registration using above credentials within next 72 hour.");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("Process to complete online registration is as below:");
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.endText();

			contentStream.beginText();
			yCordinate -= leading;
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.moveTextPositionByAmount(53, yCordinate - 35);
			contentStream.drawString("1. Open our Bank’s website:");
			contentStream.setNonStrokingColor(Color.blue);
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
			contentStream.drawString(" punjabandsindbank.co.in");
			contentStream.endText();
			
			/************************ create a link annotation ***********************/
			PDAnnotationLink txtLink = new PDAnnotationLink();

			
			/************************ add an underline *******************************/
			
			PDBorderStyleDictionary underline = new PDBorderStyleDictionary();
			underline.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
			txtLink.setBorderStyle(underline);

			
			/************************ set up the markup area *************************/
			
			float offset = (font.getStringWidth("1. Open our Bank’s website:") / 1000) * 17;
			float textWidth = (font.getStringWidth("  punjabandsindbank.co.in") / 1000) * 17;
			PDRectangle position = new PDRectangle();
			position.setLowerLeftX(offset);
			position.setLowerLeftY(yCordinate - 36f);
			position.setUpperRightX(offset + textWidth);
			position.setUpperRightY(yCordinate - 27);
			txtLink.setRectangle(position);

			/************************* add an action **********************************/
			
			PDActionURI action = new PDActionURI();
			action.setURI("http://punjabandsindbank.co.in");
			txtLink.setAction(action);

			firstPage.getAnnotations().add(txtLink);

			contentStream.beginText();
			contentStream.newLineAtOffset(startX, yCordinate);
			contentStream.newLineAtOffset(0, -5);
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -45);
			contentStream.setNonStrokingColor(Color.black);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(" 2. Click on PSB UnIC logo on the right corner, then open the corporate tab.");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(" 3. Now, click on 'Registration with kit' button.");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					" 4. Follow the instructions provided on screen and complete your online registration using the ");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"     above credentials.");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -35);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"Further, we take this opportunity to inform you that our Bank has bouquet of Retail Credit products");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("and Deposit Products as well for catering to needs of the esteemed customers");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText(
					"For any further enquiry in the matter, you may contact us on 0124-2544115/116 or write to us at:");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("omni_support@psb.co.in");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("We feel privileged to serve you.");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -30);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("Yours Truly");
			yCordinate -= leading;
			contentStream.newLineAtOffset(0, -17);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
			contentStream.showText("Punjab & Sind Bank");
			yCordinate -= fontHeight;
			yCordinate -= leading;
			contentStream.endText();
			/***************************** Load Watermark ******************************/

	        //BufferedImage image = ImageIO.read(new File(psbWatermarkPath));
			System.out.println("psbWatermark.."+psbWatermarkPath);
	        PDImageXObject image = PDImageXObject.createFromFile(psbWatermarkPath, document);
	        PDImageXObject pdxImage = LosslessFactory.createFromImage(document, image.getImage());

	        /***************************** Set the opacity *****************************/
	        PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
	        extendedGraphicsState.setNonStrokingAlphaConstant(0.36f);
	        contentStream.setGraphicsStateParameters(extendedGraphicsState);

	        /******************** Center watermark image on page ***********************/
	        PDRectangle rect = firstPage.getBBox();
	        int imageX = Math.floorDiv((Math.round(rect.getWidth()) - pdxImage.getWidth()), 2);
	        int imageY = Math.floorDiv((Math.round(rect.getHeight()) - pdxImage.getHeight()), 2);

	        contentStream.drawImage(pdxImage, imageX, imageY);
			contentStream.stroke();
			contentStream.close();
			
			/************* view the file with restricted permissions. ****************/
			AccessPermission ap = new AccessPermission();

			
			/************* Disable printing, everything else is allowed **************/
			
			ap.setCanPrint(false);
			StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerpassword, userPassWord, ap);
			spp.setEncryptionKeyLength(keyLength);
			spp.setPermissions(ap);
			document.protect(spp);
			document.save(pdfFilePath + File.separator + fileName);
			document.close();
			System.out.println("PDF Created");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file; 
	}

}