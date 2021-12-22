package ink.aos.boot.core.exception;

import lombok.Data;

@Data
public class AosException extends RuntimeException {

    private String title;

    private String detail;

    public AosException(String title) {
        this(title, null);
    }

    public AosException(String title, String detail) {
        super(title);
        this.title = title;
        this.detail = detail;
    }

}
