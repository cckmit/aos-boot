package ink.aos.boot.ap.util;

import javax.activation.MimetypesFileTypeMap;

/**
 * All rights Reserved, Designed By www.aos.ink
 *
 * @author: lichaohn@163.com
 * @date: 2019-05-22 16:20
 * @copyright: 2019 www.aos.ink All rights reserved.
 */
public class FileUtil {

    /*
     * Java文件操作 获取文件扩展名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static boolean isImage(String filename) {
        MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png tif jpg jpeg bmp");
        String mimetype = mtftp.getContentType(filename);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
}
