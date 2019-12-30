package user;

import com.github.rxyor.carp.ums.SpringWithJUnit5IT;
import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 17:28:00
 * @since 1.0.0
 */
class IUserRepositoryTest extends SpringWithJUnit5IT {

    @Autowired
    IUserRepository userRepository;

    @Transactional
    @Test
    public void findAll() {
        try {
            List<User> ret = userRepository.findAll();
            log.info("result:\n{}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Test
    public void find() {
        try {
            User ret = userRepository.find("1VWFNJHDUCZCWCY8");
            log.info("result:\n{}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}