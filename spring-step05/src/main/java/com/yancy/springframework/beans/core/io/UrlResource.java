package com.yancy.springframework.beans.core.io;


import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 读取网络文件
 */
public class UrlResource implements Resource{


    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be nnull.");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        } catch (IOException e) {
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw e;
        }
    }
}