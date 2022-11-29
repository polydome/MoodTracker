public class BackupException extends RuntimeException {
    public BackupException() {
    }

    public BackupException(String message) {
        super(message);
    }

    public BackupException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackupException(Throwable cause) {
        super(cause);
    }
}
