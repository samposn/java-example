package com.kapokframework.charset;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {


    public static void main(String[] args) throws Exception {

        String path = Objects.requireNonNull(Main.class.getResource("")).getPath();
        log.info("path : {}", URLDecoder.decode(path, Charset.defaultCharset()));

        URL url = Main.class.getClassLoader().getResource("plaintext/GB18030.txt");
        if (url != null) {
            String filePath = URLDecoder.decode(url.getPath(), Charset.defaultCharset());
            log.info("file path : {}", filePath);
            byte[] bytes = FileUtils.readFileToByteArray(new File(filePath));
            String charset = detectCharset(bytes);
            String s = new String(bytes, Charset.forName(charset));
            log.info("Charset: {}", charset);
            log.info("Default Charset: {}", Charset.defaultCharset());
            log.info("s: \n{}", s);
        }

    }

    public static String detectCharset(byte[] bytes) throws IOException {
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

    public static String detectCharset(File file) throws IOException {
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