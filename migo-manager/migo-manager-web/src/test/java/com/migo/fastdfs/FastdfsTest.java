package com.migo.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Author  知秋
 * Created by kauw on 2016/10/4.
 */
public class FastdfsTest {
    @Test
    public void testUpload()throws Exception{
        //添加依赖
        //初始化全局配置，加载配置文件
        ClientGlobal.init("D:\\idea_workspace\\migo-manager\\migo-manager-web\\src\\main\\resources\\resource\\client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient=new TrackerClient();
        //创建一个TrackerServer对象
        TrackerServer trackerServer=trackerClient.getConnection();
        //声明一个StorageServer对象
        StorageServer storageServer=null;
        //获得storageClient对象
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        //调用StorageClient对象方法上传文件
        String[] strings=storageClient.upload_file("G:\\yanzi.jpg","jpg",null);
        for (String string : strings) {
            System.out.println(string);
        }
    }
    @Test
    public void testFastdfsUtil() throws Exception {
            FastDFSClient client=new
                    FastDFSClient("D:\\idea_workspace\\migo-manager\\migo-manager-web\\src\\main\\resources\\resource\\client.conf");
            String uploadFile=
                    client.uploadFile("G:\\woyaode.jpg","jpg");
        System.out.println(uploadFile);
    }
}
