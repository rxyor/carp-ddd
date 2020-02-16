package com.github.rxyor.carp.ums.insfrastructure.repository.user.criteria;

import com.github.rxyor.carp.query.qry.UserQry;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dao.UserDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import com.github.rxyor.carp.ums.shared.common.uitl.PageUtil;
import com.github.rxyor.carp.ums.shared.common.uitl.SqlUtil;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/16 周日 23:16:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class UserCriteria {

    private final UserDAO userDAO;

    public UserDAO dao() {
        return userDAO;
    }

    public Page<UserDO> selectPage(UserQry qry) {
        Preconditions.checkArgument(qry != null,
            "查询参数不能为空");

        Specification<UserDO> spec = (Specification<UserDO>) (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>(4);
            if (qry.getId() != null) {
                conditions.add(cb.equal(root.get("id"), qry.getId()));
            }
            if (qry.getDisable() != null) {
                conditions.add(cb.equal(root.get("disable"), qry.getDisable()));
            }
            if (StringUtils.isNotBlank(qry.getUsername())) {
                conditions.add(cb.like(root.get("username"), SqlUtil.allLike(qry.getUsername())));
            }

            Predicate[] predicates = conditions.toArray(new Predicate[conditions.size()]);
            return cb.and(predicates);
        };

        return dao().findAll(spec, PageUtil.cast(qry));
    }

}
