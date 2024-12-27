package com.goldencargo.service;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DropboxService {

    private final DbxClientV2 dropboxClient;

    public DropboxService(String accessToken) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("my-app").build();
        this.dropboxClient = new DbxClientV2(config, accessToken);
    }

    public String uploadFile(String path, InputStream inputStream) throws Exception {
        FileMetadata metadata = dropboxClient.files().uploadBuilder(path)
                .withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(inputStream);
        return metadata.getPathLower();
    }

    public InputStream downloadFile(String path) throws Exception {
        try {
            DbxDownloader<FileMetadata> downloader = dropboxClient.files().download(path);
            return downloader.getInputStream();
        } catch (DownloadErrorException | GetMetadataErrorException e) {
            throw new RuntimeException("Error downloading file from Dropbox: " + path, e);
        }
    }

    public List<String> listFiles() {
        List<String> filePaths = new ArrayList<>();
        try {
            exploreFolder("", filePaths);
        } catch (DbxException e) {
            throw new RuntimeException("Error listing files from Dropbox", e);
        }
        return filePaths;
    }

    private void exploreFolder(String path, List<String> filePaths) throws DbxException {
        ListFolderResult result = dropboxClient.files().listFolderBuilder(path)
                .withRecursive(true)
                .start();

        do {
            for (Metadata metadata : result.getEntries()) {
                if (metadata instanceof FileMetadata) {
                    filePaths.add(metadata.getPathLower());
                }
            }
            result = result.getHasMore() ? dropboxClient.files().listFolderContinue(result.getCursor()) : null;
        } while (result != null);
    }
}
