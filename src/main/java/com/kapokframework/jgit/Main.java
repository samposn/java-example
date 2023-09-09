package com.kapokframework.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class Main {

    private static final String REMOTE_URL = "https://git.midea.com/dep-it/i-plan/etl.git";

    private static final String BRANCH = "release/prod";

    private static final String FILE_TO_READ = "IPLN/ON_TIME/IERP/CCID/job_IPLN_I_GCC_CODE_COMBINATIONS.kjb";

    public static void main(String[] args) throws Exception {

        DfsRepositoryDescription repoDesc = new DfsRepositoryDescription();

        try (
                InMemoryRepository repository = new InMemoryRepository(repoDesc);
                RevWalk revWalk = new RevWalk(repository);
                TreeWalk treeWalk = new TreeWalk(repository);
        ) {
            Git git = new Git(repository);
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(args[0], args[1]);
            git.fetch()
                    .setRemote(REMOTE_URL)
                    .setRefSpecs(new RefSpec("+refs/heads/" + BRANCH + ":refs/heads/" + BRANCH))
                    .setCredentialsProvider(credentialsProvider)
                    .call();

            ObjectId lastCommitId = repository.resolve("refs/heads/" + BRANCH);

            treeWalk.addTree(revWalk.parseTree(lastCommitId));
            treeWalk.setRecursive(true);

            treeWalk.setFilter(PathFilter.create(FILE_TO_READ));

            while (treeWalk.next()) {
                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);
                loader.copyTo(System.out); // TODO 这里要改为复制文件
            }
        }

    }

}
