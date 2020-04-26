package leaf;

import com.github.rxyor.carp.leaf.service.SegmentService;
import com.github.rxyor.carp.ums.SpringWithJUnit5IT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/24 周五 10:24:00
 * @since 1.0.0
 */
public class LeafTest extends SpringWithJUnit5IT {

    @Autowired
    private SegmentService segmentService;

    @Test
    public void testSegmentService(){
        segmentService.getId("leaf-segment-test");
    }
}
