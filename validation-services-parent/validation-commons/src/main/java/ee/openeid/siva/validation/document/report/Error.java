package ee.openeid.siva.validation.document.report;

import lombok.Data;

@Data
public class Error {

    private String certificateId;
    private String nameId;
    private String content;
}
