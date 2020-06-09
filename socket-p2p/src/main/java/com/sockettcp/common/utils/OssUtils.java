package com.sockettcp.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里云OSS上传下载
 */
public class OssUtils {

	public static final  String ENDPOINT="oss-cn-beijing.aliyuncs.com";
	public static final String ACCESSKEY="LTAIuSJwWbe9wy4T";
	public static final String ACCESS_SECRET="8hE1RX514FbSsKqzgtyuxztT7a24ln";
	public static final String BUCKET_URL="huantai.oss-cn-beijing.aliyuncs.com";
	public static final String BUCKET_NAME="huantaijiaoyu";
//	public static final String FILEDIR="images/";
	private String fileName;
	
	public OssUtils(String fileName){
		this.fileName=fileName;
	}
	
	/**
	 * 创建OSSClient对象
	 */
	public static OSSClient getOSSClient(){
		return new OSSClient(OssUtils.ENDPOINT,OssUtils.ACCESSKEY,OssUtils.ACCESS_SECRET);
	}
	
	/**    
	 * 新建Bucket  --Bucket权限:私有    
	 * @param bucketName bucket名称     
	 * @return true 新建Bucket成功  
	 */
	public static final boolean createBucket(OSSClient client){
		String bucketName = "oss-huantai-my";
		Bucket bucket = client.createBucket(bucketName);
		return bucketName.equals(bucket.getName());
	}

	
	/**
	 * 图片上传OSS
	 */
	
	public  String uploadObject2OSS(OSSClient ossClient,MultipartFile file,String bucketName){
		String diskName = "huantai/datas/";
		String resultStr=null;
		try {
//			String fileName=file.getOriginalFilename();
			Long fileSize=file.getSize();
			ObjectMetadata metadata= new ObjectMetadata();
			metadata.setContentLength(fileSize);
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma","no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType("");
			metadata.setContentDisposition("filename/filesize=" +fileName+"/"+fileSize+"Byte.");
			//文件上传
			ossClient.putObject(bucketName,diskName + this.fileName,file.getInputStream(),metadata);
			ossClient.shutdown();
			resultStr="http://"+OssUtils.BUCKET_NAME+"."+OssUtils.ENDPOINT+"/"+diskName + fileName;
		} catch (OSSException | ClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
	
	
	/**
	 * 图片上传OSS 带进度条
	 * @throws IOException 
	 */
	public static String uploadObject2OSS2(String bucketName,MultipartFile file,String fileName, HttpServletRequest request) throws IOException{
		String resultStr=null;
		// 创建OSSClient实例
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEY, ACCESS_SECRET);
        //createBucket(ossClient);
        
       String diskName = "huantai/datas/";
		try {
//			String fileName=file.getOriginalFilename();
			Long fileSize=file.getSize();
			ObjectMetadata metadata= new ObjectMetadata();
			metadata.setContentLength(fileSize);
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma","no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType("");
			metadata.setContentDisposition("filename/filesize=" +fileName+"/"+fileSize+"Byte.");
			File f = null;
			try {
				
				f = new File(file.getOriginalFilename());
				f.createNewFile();
				FileOutputStream fos = new FileOutputStream(f);
			    fos.write(file.getBytes());
				fos.close();
				
//				f =new File("/var/uploaded_files",file.getOriginalFilename());
//				file.transferTo(f);
//				f.deleteOnExit();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//文件上传带进度条
			ossClient.putObject(new PutObjectRequest(bucketName,diskName + fileName,f).<PutObjectRequest>withProgressListener(new PutObjectProgressListener(request.getSession())));
			resultStr="http://"+OssUtils.BUCKET_NAME+"."+OssUtils.ENDPOINT+"/"+diskName + fileName;
			f.delete();
		} catch (OSSException | ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			ossClient.shutdown();
		}
		return resultStr;
	}
	
	/**
	 * 定义图片上传类型及返回类型
	 */
	
	public static String getContentType(String fileName){
		String fileExtension=fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)){
			return "image/bmp";
		}
		else if(".gif".equalsIgnoreCase(fileExtension)){
			return "image/gif";
		}
		else if(".jpeg".equalsIgnoreCase(fileExtension)||".jpg".equalsIgnoreCase(fileExtension)||".png".equalsIgnoreCase(fileExtension)){
			return "image/jpeg";
		}
		else if(".mp4".equalsIgnoreCase(fileExtension)){
			return "video/mp4";
		}
		else if(".mp3".equalsIgnoreCase(fileExtension)){
			return "music/mp3";
		}
		else{
			fileName = "application/octet-stream";
		}
		return fileName;
	}
	
	/**
	 * 文件上传OSS
	 */
	
	public  String uploadObject(MultipartFile headPic){
		OSSClient ossClient=getOSSClient();
	//	createBucket(ossClient);
		return uploadObject2OSS(ossClient,headPic, OssUtils.BUCKET_NAME);
	}
	
	
	/**
	 * 上传
	 * 进度监听  
	 */
	public static class PutObjectProgressListener implements ProgressListener {
		private HttpSession session;
		 private long bytesWritten = 0;
		 private long totalBytes = -1;
		 private boolean succeed = false;
		 private int percent = 0;

		 // 构造方法中加入session
		 public PutObjectProgressListener(){
		 }

		 public PutObjectProgressListener(HttpSession mSession){
			 this.session = mSession;
			 session.setAttribute("upload_percent", percent);
		 }

		@Override
		public void progressChanged(ProgressEvent progressEvent) {
			long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
				case TRANSFER_STARTED_EVENT:
					// logger.info("Start to upload......");
					break;
				case REQUEST_CONTENT_LENGTH_EVENT:
					this.totalBytes = bytes;
					// logger.info(this.totalBytes +
					// " bytes in total will be uploaded to OSS");
					break;
				case REQUEST_BYTE_TRANSFER_EVENT:
					this.bytesWritten += bytes;
					if (this.totalBytes != -1){
						percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
						// 将进度percent放入session中
						session.setAttribute("upload_percent", percent);
						//logger.debug(bytes + " bytes have been written at this time, upload progress: " + percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
					}else{
						//logger.debug(bytes + " bytes have been written at this time, upload ratio: unknown" + "(" + this.bytesWritten + "/...)");
					}
					break;
				case TRANSFER_COMPLETED_EVENT:
                    this.succeed = true;
                    break;
				case TRANSFER_FAILED_EVENT:
					break;    
	
				default:
					break;
			}
          //控制台打印进度            
         // System.out.println("percent:" + percent);
			
		}
						
		 public boolean isSucceed(){
			 return succeed;
	     }
	}

	public static String downloadFile(String objectName){
		String path = "D:/huantai/download/";
		String file = null;
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEY, ACCESS_SECRET);
		try {
			 String filePath = "/"+objectName.substring(objectName.lastIndexOf("/"));
			
	    	 File f = new File(path);
	    	 if(!f.exists()){
	    		 f.mkdirs();
	    	 }
	    	 
			// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
			ossClient.getObject(new GetObjectRequest(OssUtils.BUCKET_NAME, objectName), new File(path+filePath));
			
			file = path+filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 关闭OSSClient。
		ossClient.shutdown();
		return file;
	}
	
}
