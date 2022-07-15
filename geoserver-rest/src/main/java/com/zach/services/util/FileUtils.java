package com.zach.services.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/**
 * <p>
 * 文件操作类
 * </p>
 *
 * @author liugh
 * @since 2018/3/20
 */
public class FileUtils {

    private static ResourceBundle bundle = ResourceBundle.getBundle("constant");

    //配置文件里获得,根目录地址 /data/test-version
    public static String fileUploadPath =bundle.getString("file-upload.dir");

    /**
     * 判断当前文件是否是zip文件
     *
     * @param fileName
     *            文件名
     * @return true 是
     */
    public static boolean isZip(String fileName) {
        return fileName.toLowerCase().endsWith(Constant.FilePostFix.ZIP_FILE);
    }


    /**
     * 删除目录
     * @param fileName
     */
    public static void removeDocument(String fileName){
        File file=new File(fileName);
        if(file.exists() && file.isFile()) {
            file.delete();
        }
        if(file.isDirectory()){
            delDir(fileName);
        }
        if (fileName.lastIndexOf(Constant.FilePostFix.ZIP_FILE) > 0) {
            delDir(fileName.substring(0,fileName.lastIndexOf(Constant.FilePostFix.ZIP_FILE))+"/");
        }

    }


    /**
     * 获取坐标系
     * @param filePath
     * @return
     */
    public static String getCoordinate(String filePath){
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line;
            StringBuilder sb  =new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            reader.close();
            String str = sb.toString();
            String substring = str.substring(str.indexOf("\"") + 1, str.indexOf("\","));
            return parseCoordinate(substring);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static String parseCoordinate(String  coordinate){
        switch (coordinate){
            case "Beijing_1954_GK_Zone_21N": return "EPSG:21481";
            case "Beijing_1954_GK_Zone_22N": return "EPSG:21482";
            case "GCS_Xian_1980": return "EPSG:4610";
            case "GCS_WGS_1984": return "EPSG:4326";
            default:return null;
        }
    }

    /**
     * 检查第一级目录是否有.shp文件
     * @param sourcePath
     * @return
     */
    public static boolean checkZipFile(String sourcePath){
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        ZipFile zipFile =null;
        try {
            File sourceFile = new File(sourcePath);
            zipFile = new ZipFile(sourcePath, "gbk");
            if ((!sourceFile.exists()) && (sourceFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                if (zipEnt.isDirectory()) {
                    return false;
                }
                if(zipEnt.getName().endsWith(".shp")){
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(null!=zipFile){
                    zipFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static List<String> listFile = new ArrayList();

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     * (把指定文件夹下的所有文件目录和文件都压缩到指定文件夹下)
     * @param sourceFilePath
     *            :待压缩的文件路径
     * @param zipFilePath
     *            :压缩后存放路径
     * @param fileName
     *            :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName)throws  Exception{
        boolean flag = false;
        FileOutputStream fos =null;
        ZipOutputStream zos =null;
        BufferedInputStream bis =null;
        FileInputStream  fis =null;
        BufferedOutputStream bufferedOutputStream =null;
        File sourceFile = new File(sourceFilePath);
        if(sourceFile.exists() == false){
            throw new Exception("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{
            try {
                File zipFile = new File(zipFilePath +fileName );
                if(zipFile.exists()){
                    throw new Exception(zipFilePath + "目录下存在名字为:" + fileName +Constant.FilePostFix.ZIP_FILE +"打包文件.");
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        throw new Exception("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        bufferedOutputStream = new BufferedOutputStream(fos);
                        zos = new ZipOutputStream(bufferedOutputStream);
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, Constant.BYTE_BUFFER *Constant.BUFFER_MULTIPLE);
                            int read;
                            while((read=bis.read(bufs, 0, Constant.BYTE_BUFFER *Constant.BUFFER_MULTIPLE)) != -1){
                                zos.write(bufs,0,read);
                            }
                            fis.close();
                            bis.close();
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally{
                //关闭流
                try {
                    if (null != bis) {
                        bis.close();
                    }
                    if (null != zos) {
                        zos.close();
                    }
                    if (null != bufferedOutputStream) {
                        bufferedOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


/*    public static boolean updateLayerNameByZipFile(String sourcePath,String targetLayerName) {
        File file = new File(sourcePath);
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (!ComUtil.isEmpty(fileList)) {
                for (String fileName : fileList) {
                    File oldName = new File(sourcePath + File.separator + fileName);
                    File newName = new File(sourcePath + File.separator + targetLayerName + fileName.substring(fileName.lastIndexOf(".")));
                    if (".xml".equals(fileName.substring(fileName.lastIndexOf(".")))) {
                        newName = new File(sourcePath + File.separator + targetLayerName + fileName.substring(fileName.lastIndexOf(".shp")));
                    }
                    oldName.renameTo(newName);
                }
                return true;
            }
            return false;
        }
        return false;
    }*/

    /**
     * 保存文件到临时目录
     * @param inputStream 文件输入流
     * @param fileName 文件名
     */
    public static void savePic(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            // 保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(fileUploadPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String saveFile(InputStream file, int type, String name, String tag) throws Exception {
        Date time = new Date();
        String fileName = fileName(time, type, name,tag);
        File newFile = getNewFile(fileName);
        File oldFile = createTemporaryFile(file, name);
        copyFile(newFile, new FileInputStream(oldFile));
        oldFile.delete();
        return fileName;
    }

    public static File createTemporaryFile(InputStream file, String name) throws Exception {
        File temp = new File(name);
        OutputStream out = new FileOutputStream(temp);
        try {
            int byteCount = 0;
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = file.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
        }
        finally {
            try {
                file.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
        return temp;
    }

    private static void copyFile(File newFile, FileInputStream file) throws Exception {
        FileOutputStream outFile = new FileOutputStream(newFile);
        FileChannel inC = file.getChannel();
        FileChannel outC = outFile.getChannel();
        int length = 2097152;
        while (true) {
            if (inC.position() == inC.size()) {
                inC.close();
                outC.close();
                outFile.close();
                file.close();
                return ;
            }
            if ((inC.size() - inC.position()) < 20971520) {
                length = (int) (inC.size() - inC.position());
            }else {
                length = 20971520;
            }
            inC.transferTo(inC.position(), length, outC);
            inC.position(inC.position() + length);
        }

    }

    public static File getNewFile(String fileName) throws IOException {
        String filePath = bundle.getString("file-upload.dir") + fileName;
        File newFile = new File(filePath);
        File fileParent = newFile.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        if (!newFile.exists()){
            newFile.createNewFile();
        }
        return newFile;
    }

    /**
     * 文件保存路径
     * @param time
     * @param type
     * @param name
     * @return
     */
    private static String fileName(Date time, int type, String name,String tag) {
        StringBuffer str = new StringBuffer();
        if (type== Constant.FileType.FILE_IMG) {
            str.append(Constant.FileType.FILE_IMG_DIR);
        }
        if (type==Constant.FileType.FILE_ZIP) {
            str.append(Constant.FileType.FILE_ZIP_DIR);
        }
        if (type==Constant.FileType.FILE_VEDIO) {
            str.append(Constant.FileType.FILE_VEDIO_DIR);
        }
        if (type==Constant.FileType.FILE_APK) {
            str.append(Constant.FileType.FILE_APK_DIR);
        }
        if (type==Constant.FileType.FIVE_OFFICE) {
            str.append(Constant.FileType.FIVE_OFFICE_DIR);
        }
        str.append(DateTimeUtil.formatDatetoString(time));
        str.append("/");
        str.append(DateTimeUtil.getSystemDate());

            str.append(tag);

        str.append("-"+name);
        return str.toString();
    }
    public static String getFileUrl(String fileDir){
        return bundle.getString("file-upload.url") + fileDir;
    }


    /**
     * 删除文件目录
     * @param path
     */
    private static void delDir(String path){
        File dir=new File(path);
        if(dir.exists()){
            File[] tmp=dir.listFiles();
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].isDirectory()){
                    delDir(path+File.separator+tmp[i].getName());
                }else{
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }


    /**
     * 解压zip格式的压缩文件到指定位置
     *
     * @param sourcePath
     *            压缩文件
     * @param targetPath
     *            解压目录
     * @throws Exception
     */
    public static boolean unZipFiles(String sourcePath, String targetPath) throws Exception {
        System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
        InputStream is =null;
        BufferedInputStream bis =null;
        try {
            (new File(targetPath)).mkdirs();
            File sourceFile = new File(sourcePath);
            // 处理中文文件名乱码的问题
            ZipFile zipFile = new ZipFile(sourcePath, "UTF-8");
            if ((!sourceFile.exists()) && (sourceFile.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            String strPath, gbkPath, strtemp;
            File tempFile = new File(targetPath);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    // 读写文件
                    is = zipFile.getInputStream((ZipEntry) zipEnt);
                    bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    // 建目录
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if ("/".equalsIgnoreCase(strsubdir.substring(i, i + 1))) {
                            String temp = strPath + File.separator + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists()) {
                                subdir.mkdir();
                            }
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.flush();
                    fos.close();
                    bos.close();
                }
            }
            zipFile.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteUploadedFile(String fileName) {
        String filePath = bundle.getString("file-upload.dir") + fileName;
        File file =  new File(filePath);
        if(file.exists()){
            if(file.isFile()) {
                file.delete();
            }else{
                removeDocument(fileName);
            }
            return true;
        }else{
            return false;
        }
    }

    public static int getFileType(String originalFilename) {
        String postFix = originalFilename.split("//.")[originalFilename.split("//.").length-1];
        if(Arrays.asList(Constant.FilePostFix.IMAGES).contains(postFix)){
            return Constant.FileType.FILE_IMG;
        }
        if(Arrays.asList(Constant.FilePostFix.ZIP).contains(postFix)){
            return Constant.FileType.FILE_ZIP;
        }
        if(Arrays.asList(Constant.FilePostFix.VIDEO).contains(postFix)){
            return Constant.FileType.FILE_VEDIO;
        }
        if(Arrays.asList(Constant.FilePostFix.APK).contains(postFix)){
            return Constant.FileType.FILE_APK;
        }
        if(Arrays.asList(Constant.FilePostFix.OFFICE).contains(postFix)){
            return Constant.FileType.FIVE_OFFICE;
        }
        return Constant.FileType.FILE_IMG;
    }
}
