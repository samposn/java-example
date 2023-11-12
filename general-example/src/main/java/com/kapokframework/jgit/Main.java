package com.kapokframework.jgit;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.util.AntPathMatcher;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static void main(String[] args) throws Exception {

        JSONObject requestData = new JSONObject();

        requestData.put("u", args[0]);
        requestData.put("p", args[1]);
        requestData.put("repoUrl", "https://git.midea.com/dep-it/i-plan/etl.git");
        requestData.put("branch", "release/uat");
        requestData.put("sourcePath", "IPLN/REAL_TIME/IPLN/Actual_P_L/*.*");
        requestData.put("targetPath", "/mnt/nas/kettle-spaces/IPLN/REAL_TIME/IPLN/Actual_P_L/");

        sync(requestData);

    }

    private static void sync(JSONObject requestData) throws Exception {
        String u = requestData.getString("u");
        String p = requestData.getString("p");
        String repoUrl = requestData.getString("repoUrl");
        String branch = requestData.getString("branch");
        String sourcePath = requestData.getString("sourcePath");
        String targetPath = requestData.getString("targetPath");

        DfsRepositoryDescription repoDesc = new DfsRepositoryDescription();
        try (
                InMemoryRepository repository = new InMemoryRepository(repoDesc);
                RevWalk revWalk = new RevWalk(repository);
                TreeWalk treeWalk = new TreeWalk(repository);
                Git git = new Git(repository)
        ) {
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(u, p);
            git.fetch()
                    .setRemote(repoUrl)
                    .setRefSpecs(new RefSpec("+refs/heads/" + branch + ":refs/heads/" + branch))
                    .setCredentialsProvider(credentialsProvider)
                    .call();

            boolean isPattern = pathMatcher.isPattern(sourcePath);

            ObjectId lastCommitId = repository.resolve("refs/heads/" + branch);
            treeWalk.addTree(revWalk.parseTree(lastCommitId));
            if (!isPattern) {
                treeWalk.setFilter(PathFilter.create(sourcePath));
            }
            treeWalk.setRecursive(true);

            int i = 1;
            while (treeWalk.next()) {
                String pathString = treeWalk.getPathString();
                if (isPattern && !pathMatcher.matchStart(sourcePath, pathString)) {
                    continue;
                }

                String subPath = isPattern ? pathMatcher.extractPathWithinPattern(sourcePath, pathString) : StringUtils.difference(sourcePath, pathString);
                String filePath = pathMatcher.combine(targetPath, subPath);

                log.info("{}", i++);
                log.info("isPattern: {}", isPattern);
                log.info("matched path: {}", pathString);
                log.info("    sub path: {}", subPath);
                log.info(" source path: {}", sourcePath);
                log.info(" target path: {}", targetPath);
                log.info("   file path: {}", filePath);

                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);
                byte[] bytes = loader.getBytes();
                String charset = detectCharset(bytes);
                if (StringUtils.isNotBlank(charset)) {
                    FileUtils.writeStringToFile(new File(filePath), new String(bytes, Charset.forName(charset)), Charset.defaultCharset());
                } else {
                    FileUtils.writeByteArrayToFile(new File(filePath), bytes);
                }
            }
        }
    }

    private static String detectCharset(byte[] bytes) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byte[] buf = new byte[4096];
        int read;
        detector.reset();
        while ((read = byteArrayInputStream.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(bytes, 0, read);
        }
        detector.dataEnd();
        return detector.getDetectedCharset();
    }

}
