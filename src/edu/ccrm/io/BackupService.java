package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private static final Path EXPORT_DIR = Paths.get("data", "exports");
    private static final Path BACKUP_ROOT_DIR = Paths.get("data", "backups");

    public void performBackup() throws IOException {
        if (!Files.exists(EXPORT_DIR) || Files.list(EXPORT_DIR).count() == 0) {
            System.out.println("Export directory is empty or does not exist. Nothing to back up.");
            return;
        }

        // Create a timestamped folder name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = BACKUP_ROOT_DIR.resolve("backup_" + timestamp);

        Files.createDirectories(backupDir); // Create the new backup folder

        // Using Files.walk to copy files
        try (var stream = Files.walk(EXPORT_DIR, 1)) { // depth 1 to only get files in export dir
            stream.filter(Files::isRegularFile).forEach(sourcePath -> {
                try {
                    Path destPath = backupDir.resolve(sourcePath.getFileName());
                    Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to copy file during backup: " + e.getMessage());
                }
            });
        }
        
        System.out.println("Backup completed successfully to: " + backupDir.toAbsolutePath());
    }
}