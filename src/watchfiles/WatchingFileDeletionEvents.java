package watchfiles;

/*
 * Copyright (c) 2000-2009 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
import com.teamdev.filewatch.FileEvent;
import com.teamdev.filewatch.FileEventsAdapter;
import com.teamdev.filewatch.FileWatcher;
import com.teamdev.filewatch.WatchingAttributes;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This example demonstrates how to monitor file deletion events in a watching folder only.
 */
public class WatchingFileDeletionEvents {
    public static void main(String[] args) throws Exception {
        final Logger logger = Logger.getLogger(WatchingFileDeletionEvents.class.getName());

        File tempFile = File.createTempFile("tmp", "tmp");
        tempFile.deleteOnExit();
        File watchingFolder = tempFile.getParentFile();

        FileWatcher watcher = FileWatcher.create(watchingFolder);

        watcher.addFileEventsListener(new FileEventsAdapter() {
            @Override
            public void fileDeleted(FileEvent.Deleted e) {
                logger.info(e.toString());
            }
        });

        Set<WatchingAttributes> watchingAttributes = new HashSet<WatchingAttributes>();
        // Monitor file names
        watchingAttributes.add(WatchingAttributes.Subtree);
        watchingAttributes.add(WatchingAttributes.FileName);

        watcher.setOptions(watchingAttributes);

        watcher.start();

        System.out.println("File watcher started. Press 'Enter' to terminate applicaiton.");
        System.in.read();

        watcher.stop();
        System.out.println("File watcher stopped.");
    }
}
