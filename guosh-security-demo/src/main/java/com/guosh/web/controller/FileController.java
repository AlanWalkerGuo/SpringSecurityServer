package com.guosh.web.controller;

import com.guosh.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${uploadfiledir.filePath}")
    private String fileDataStorePath;//文件上传地址

    @RequestMapping(method = RequestMethod.POST)
    public FileInfo upload(@RequestParam("file") MultipartFile file) throws IOException {
        //文件名
        System.out.println(file.getOriginalFilename());
        //文件大小
        System.out.println(file.getSize());
        //获取文件后缀名
        String ext=StringUtils.getFilenameExtension(file.getOriginalFilename());

        File fileDir = new File(fileDataStorePath);
        //判断是否创建目录
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs() || !fileDir.exists()) { // 创建目录失败
                throw new RuntimeException("无法创建目录！");
            }
        }

        File localFile=new File(fileDataStorePath, UUID.randomUUID().toString().replace("-", "")+"."+ext);
        file.transferTo(localFile);
        //返回上传的路径地址
        return new FileInfo(localFile.getAbsolutePath());

    }
    //下载文件
    @RequestMapping(value ="/{id}" ,method = RequestMethod.GET)
    public void download(@PathVariable String id, HttpServletResponse response){
        try(InputStream inputStream = new FileInputStream(new File(fileDataStorePath,"13a2c075b7f44025bbb3c590f7f372eb.txt"));
            OutputStream outputStream=response.getOutputStream();){

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename="+"13a2c075b7f44025bbb3c590f7f372eb.txt\"");
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
