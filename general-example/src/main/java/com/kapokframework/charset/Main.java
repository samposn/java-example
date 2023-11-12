package com.kapokframework.charset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {


    public static void main(String[] args) throws Exception {

        String path = Main.class.getResource("/").getPath();
        log.info("url1 : {}", path);

        URL url = Main.class.getClassLoader().getResource("plaintext/sql.txt");
        if (url != null) {
            byte[] bytes = FileUtils.readFileToByteArray(new File(url.getFile()));
            String charset = detect(bytes);
//        String s = new String(bytes, Charset.forName(charset));
            log.info("Charset: {}", charset);
            log.info("Default Charset: {}", Charset.defaultCharset());
//        log.info("s: {}", s);
        }

    }

    public static String detect(byte[] bytes) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byte[] buf = new byte[10];
        int read;
        detector.reset();
            while ((read = byteArrayInputStream.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(bytes, 0, read);
            }
            detector.dataEnd();
        return detector.getDetectedCharset();
    }

    public static String detect(File file) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];
            int read;
            detector.reset();
            while ((read = fileInputStream.read(bytes)) > 0 && !detector.isDone()) {
                detector.handleData(bytes, 0, read);
            }
            detector.dataEnd();
        }
        return detector.getDetectedCharset();
    }

}