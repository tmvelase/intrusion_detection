package watchfiles;

/*
 * Copyright (c) 2000-2009 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */
import com.teamdev.filewatch.*;

import java.io.File;
import java.util.Set;
import java.util.EnumSet;
import java.util.logging.Logger;

/**
 * This example demonstrates how to monitor filtered by file mask files only.
 */
public class WatchingFilteredFileEvents {
    public static void main(String[] args) throws Exception {
        final Logger logger = Logger.getLogger(WatchingFilteredFileEvents.class.getName());

        File tempFile = File.createTempFile("tmp", "tmp");
        tempFile.deleteOnExit();
        File watchingFolder = tempFile.getParentFile();

        FileWatcher watcher = FileWatcher.create(watchingFolder);

        watcher.addFileEventsListener(new FileEventsAdapter() {
            @Override
            public void fileAdded(FileEvent.Added e) {
                logger.info(e.toString());
            }

            @Override
            public void fileChanged(FileEvent.Changed e) {
                logger.info(e.toString());
            }

            @Override
            public void fileRenamed(FileEvent.Renamed e) {
                logger.info(e.toString());
            }

            @Override
            public void fileDeleted(FileEvent.Deleted e) {
                logger.info(e.toString());
            }
        });

        // Monitor all events excluding directories
        Set<WatchingAttributes> watchingAttributes = EnumSet.allOf(WatchingAttributes.class);
        watchingAttributes.remove(WatchingAttributes.DirectoryName);
        watcher.setOptions(watchingAttributes);

        // Specify monitoring only java and class files
        watcher.setFilter(new FileMaskFilter("*.java;*.class"));

        watcher.start();

        System.out.println("File watcher started. Press 'Enter' to terminate applicaiton.");
        System.in.read();

        watcher.stop();
        System.out.println("File watcher stopped.");
    }
}