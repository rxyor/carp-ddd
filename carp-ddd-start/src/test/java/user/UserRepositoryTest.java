package user;

import com.github.rxyor.carp.ddd.SpringWithJUnit5IT;
import com.github.rxyor.carp.ddd.domain.user.User;
import com.github.rxyor.carp.ddd.domain.user.UserRepository;
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
class UserRepositoryTest extends SpringWithJUnit5IT {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    public void findAll() {
        try {
            List<User> userList = userRepository.findAll();
            log.info("result:\n{}",userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}