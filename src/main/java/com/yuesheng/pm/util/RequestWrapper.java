package com.yuesheng.pm.util;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

public class RequestWrapper extends HttpServletRequestWrapper {
    private String body;
    private String method;
    private Map<String, String[]> params;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.method = request.getMethod();
        params = request.getParameterMap();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                body = IOUtils.toString(inputStream, "UTF-8");
            } else {
                body = "";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes("UTF-8"));
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), "UTF-8"));
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<String>(this.params.keySet());
        return vector.elements();
    }

    @Override
    public String getParameter(String name) {
        String[] value = params.get(name);
        if (value == null || value.length == 0) {
            return null;
        }
        return value[0];
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.params.get(name);
    }


    public String getBody() {
        return this.body;
    }
}
