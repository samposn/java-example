package com.kapokframework.jgit;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.File;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class Main {

    private static final String REMOTE_URL = "https://gitee.com/zhijiantianya/yudao-cloud.git"; // "https://git.midea.com/dep-it/i-plan/etl.git";

    private static final String BRANCH = "master"; // "release/prod";

    public static void main(String[] args) throws Exception {

        String sourcePath = "sql/dm"; // "IPLN/ON_TIME/IERP/CCID/job_IPLN_I_GCC_CODE_COMBINATIONS.kjb";
        String targetPath = "/Users/zhangweiming/test1/a";

        sync(sourcePath, targetPath);

    }

    private static void sync(String sourcePath, String targetPath) throws Exception {
        DfsRepositoryDescription repoDesc = new DfsRepositoryDescription();
        try (
                InMemoryRepository repository = new InMemoryRepository(repoDesc);
                RevWalk revWalk = new RevWalk(repository);
                TreeWalk treeWalk = new TreeWalk(repository);
                Git git = new Git(repository)
        ) {
//            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(args[0], args[1]);
            git.fetch()
                    .setRemote(REMOTE_URL)
                    .setRefSpecs(new RefSpec("+refs/heads/" + BRANCH + ":refs/heads/" + BRANCH))
//                    .setCredentialsProvider(credentialsProvider)
                    .call();

            ObjectId lastCommitId = repository.resolve("refs/heads/" + BRANCH);

            treeWalk.addTree(revWalk.parseTree(lastCommitId));
            treeWalk.setFilter(PathFilter.create(sourcePath));
            treeWalk.setRecursive(true);

            while (treeWalk.next()) {
                String subPathString = subPathString(sourcePath, treeWalk.getPathString());
                String filePath;
                if (!subPathString.contains("/")) {
                    if (targetPath.endsWith("/")) {
                        filePath = targetPath.concat(subPathString);
                    } else {
                        filePath = targetPath;
                    }
                } else {
                    filePath = targetPath.concat(targetPath.endsWith("/") ? "" : "/").concat(subPathString);
                }

                System.out.printf("====> sourcePath: %s ==== targetPath: %s ==== originFilePath: %s%n", sourcePath, targetPath, treeWalk.getPathString());
                System.out.printf("====> subPathString: %s ==== filePath: %s%n", subPathString, filePath);

                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);
                FileUtils.writeByteArrayToFile(new File(filePath), loader.getBytes());
            }
        }
    }

    private static String subPathString(String searchPath, String fullPath) {
        String[] pathStringArray = StringUtils.split(searchPath, "/");
        String lastPath = pathStringArray[pathStringArray.length - 1];
        String difference = StringUtils.difference(searchPath, fullPath);
        return lastPath.concat(searchPath.endsWith("/") ? "/" : "").concat(difference);
    }

}
