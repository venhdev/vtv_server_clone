package hcmute.kltn.vtv.model.data.vendor.response;

import hcmute.kltn.vtv.model.dto.StatisticsDTO;
import hcmute.kltn.vtv.model.extra.ResponseAbstract;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsResponse extends ResponseAbstract {

    private int count;
    private int totalOrder;
    private Long totalMoney;
    private String username;
    private Date dateStart;
    private Date dateEnd;

    private List<StatisticsDTO> statisticsDTOs;

}
