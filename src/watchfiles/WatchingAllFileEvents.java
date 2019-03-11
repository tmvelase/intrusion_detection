package watchfiles;

/*
 * Copyright (c) 2000-2009 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
import com.teamdev.filewatch.FileEvent;
import com.teamdev.filewatch.FileEventsListener;
import com.teamdev.filewatch.FileWatcher;

import java.io.File;
import java.util.logging.Logger;

/**
 * This example demonstrates how to monitor all file system events in a watching folder.
 */
public class WatchingAllFileEvents {

    public static void main(String[] args) throws Exception {
        final Logger logger = Logger.getLogger(WatchingAllFileEvents.class.getName());

        File tempFile = File.createTempFile("tmp", "tmp");
        tempFile.deleteOnExit();
        File watchingFolder = tempFile.getParentFile();

        FileWatcher watcher = FileWatcher.create(watchingFolder);

        watcher.addFileEventsListener(new FileEventsListener() {
            public void fileAdded(FileEvent.Added e) {
                logger.info(e.toString());
            }

            public void fileDeleted(FileEvent.Deleted e) {
                logger.info(e.toString());
            }

            public void fileChanged(FileEvent.Changed e) {
                logger.info(e.toString());
            }

            public void fileRenamed(FileEvent.Renamed e) {
                logger.info(e.toString());
            }
        });

        watcher.start();

        System.out.println("File watcher started. Press 'Enter' to terminate applicaiton.");
        System.in.read();

        watcher.stop();
        System.out.println("File watcher stopped.");
    }
}