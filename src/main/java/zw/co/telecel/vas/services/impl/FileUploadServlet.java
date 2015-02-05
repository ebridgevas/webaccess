package zw.co.telecel.vas.services.impl;

/**
 * @author david@tekeshe.com
 */
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.vo.FileMeta;

//this to be used with Java Servlet 3.0 API
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // this will store uploaded files
    private static List<FileMeta> files = new LinkedList<FileMeta>();
    /***************************************************
     * URL: /upload
     * doPost(): upload the files and other parameters
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        // 1. Upload File Using Java Servlet API
        //files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));

        // 1. Upload File Using Apache FileUpload
        files.addAll(MultipartRequestHandler.uploadByApacheFileUpload(request));

        // Remove some files
        while(files.size() > 20) {
            files.remove(0);
        }

        FileMeta file = null;
        for ( FileMeta fileMeta : files) {
            if ( fileMeta.getContent().available() > 0 ) {
                file = fileMeta;
                break;
            }
        }

        if ( file == null ) {
            throw new IOException("No picture uploaded.");
        }
//        String filex = IOUtils.toString(files.get(0).getContent(), "UTF-8");
        System.out.println("file type : " + files.get(0).getFileType());
        files.get(0).getFileSize();
        System.out.println("file : " + file );

//        try {
//            UserDao.updateUserPhoto( request.getParameterMap().get("mobile-number")[0],
//                                     file.getFileType(),
//                                     0,
//                                     file.getContent());
//        } catch (DatabaseException e) {
//            throw new IOException( e );
//        }
        String fileExtension =  files.get(0).getFileType().split("/")[1];
        String mobileNumber = request.getParameterMap().get("mobile-number")[0];
        String filename = "/prod/ebridge/images/photos/" + mobileNumber + "." + fileExtension;
        OutputStream out = new FileOutputStream(new File( filename ));
        InputStream in = file.getContent();
        System.out.println("file size : " + in.available() );
        byte[] buffer = new byte[ in.available()];
        in.read( buffer );
        out.write( buffer );
        out.close();

        try {
            UserDao.updateUserPhoto(mobileNumber,  mobileNumber + "." + fileExtension );
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        // 2. Set response type to json
        response.setContentType("application/json");

        // 3. Convert List<FileMeta> into JSON format
        ObjectMapper mapper = new ObjectMapper();

        // 4. Send result to client

        try {
            mapper.writeValue(response.getOutputStream(), files);
        } catch (JsonMappingException e) {
            throw new IOException(e);
        }
    }
    /***************************************************
     * URL: /upload?f=value
     * doGet(): get file of index "f" from List<FileMeta> as an attachment
     ****************************************************/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        // 1. Get f from URL upload?f="?"
        String value = request.getParameter("f");

        // 2. Get the file of index "f" from the list "files"
        FileMeta getFile = files.get(Integer.parseInt(value));

        try {
            // 3. Set the response content type = file content type
            response.setContentType(getFile.getFileType());

            // 4. Set header Content-disposition
            response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");

            // 5. Copy file inputstream to response outputstream
            InputStream input = getFile.getContent();
            OutputStream output = response.getOutputStream();
            byte[] buffer = new byte[1024*10];

            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }

            output.close();
            input.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}