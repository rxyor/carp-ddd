package timezone;

import java.util.Date;
import org.junit.jupiter.api.Test;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/3/8 周日 20:11:00
 * @since 1.0.0
 */
public class TimezoneTest {

    @Test
    public void curTimezone(){
        Date date = new Date();
        System.out.println(date);
    }

}
