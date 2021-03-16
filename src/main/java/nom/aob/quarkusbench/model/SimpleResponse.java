package nom.aob.quarkusbench.model;

import lombok.*;
import nom.aob.quarkusbench.utils.Utils;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponse {
    private String hostString;
    private String pathString;
    private String timeString;
    private Integer randomInteger;
    private Long threadID;


    public static SimpleResponse newSimpleResponse(String path) {
        return new SimpleResponse(
                Utils.getHostname(),
                path,
                Utils.getCurrentTimeString(),
                Utils.newRandomInt(),
                Thread.currentThread().getId()
        );
    }

}
