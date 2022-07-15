package com.zach.services.util;

public class Constant {

    public static final int BYTE_BUFFER = 1024;

    public static final int BUFFER_MULTIPLE = 10;
    public static final int AtlasStyleType = 10;

    public class AtlasStyleType01{
        public static final String AtlasStyleType = "1";
    }

    /**
     * 县图集类型
     */
    public class AtlasType {
        //点位图
        public static final String ATLAS_TYPE_1 = "1";
        //单元图
        public static final String ATLAS_TYPE_2 = "2";
        //土壤类型图
        public static final String ATLAS_TYPE_3 = "3";
        //评价结果表
        public static final String ATLAS_TYPE_4 = "4";
    }

    public class FileType{
        public static final int FILE_IMG = 1;
        public static final int FILE_ZIP = 2;
        public static final int FILE_VEDIO= 3;
        public static final int FILE_APK = 4;
        public static final int FIVE_OFFICE = 5;
        public static final String FILE_IMG_DIR= "/img/";
        public static final String FILE_ZIP_DIR= "/zip/";
        public static final String FILE_VEDIO_DIR= "/video/";
        public static final String FILE_APK_DIR= "/apk/";
        public static final String FIVE_OFFICE_DIR= "/office/";
    }


    public static String parseAtlasTypeToName(String type){
        String typeName="";
        switch (type){
            case AtlasType.ATLAS_TYPE_1: typeName="bitMap"; break;
            case AtlasType.ATLAS_TYPE_2: typeName="unitMap"; break;
            case AtlasType.ATLAS_TYPE_3: typeName="typeMap"; break;
            default:break;
        }
        return  typeName;
    }

    public static class FilePostFix{
        public static final String ZIP_FILE =".zip";

        public static final String [] IMAGES ={"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP"};
        public static final String [] ZIP ={"ZIP","zip","rar","RAR"};
        public static final String [] VIDEO ={"mp4","MP4","mpg","mpe","mpa","m15","m1v", "mp2","rmvb"};
        public static final String [] APK ={"apk","exe"};
        public static final String [] OFFICE ={"xls","xlsx","docx","doc","ppt","pptx"};

    }

    public class Atlas {
        public static final int ATLAS_FIELD_STATUS_HIDE = 0;
        public static final int ATLAS_FIELD_STATUS_SHOW = 1;
    }

}