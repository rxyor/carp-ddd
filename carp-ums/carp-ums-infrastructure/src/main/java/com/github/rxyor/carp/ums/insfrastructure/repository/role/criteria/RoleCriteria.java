package com.github.rxyor.carp.ums.insfrastructure.repository.role.criteria;

import com.github.rxyor.carp.query.qry.role.RoleQry;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dao.RoleDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
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
 * @date 2020/2/18 周二 14:49:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class RoleCriteria {

    private final RoleDAO roleDAO;

    public RoleDAO dao() {
        return roleDAO;
    }

    public Page<RoleDO> selectPage(RoleQry qry){
        Preconditions.checkArgument(qry != null,
            "查询参数不能为空");

        Specification<RoleDO> spec = (Specification<RoleDO>) (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>(4);
            if (qry.getId() != null) {
                conditions.add(cb.equal(root.get("id"), qry.getId()));
            }
            if (qry.getDisable() != null) {
                conditions.add(cb.equal(root.get("disable"), qry.getDisable()));
            }
            if (StringUtils.isNotBlank(qry.getRoleName())) {
                conditions.add(cb.like(root.get("roleName"), SqlUtil.allLike(qry.getRoleName())));
            }
            if (StringUtils.isNotBlank(qry.getRoleCode())) {
                conditions.add(cb.like(root.get("roleCode"), SqlUtil.allLike(qry.getRoleCode())));
            }

            Predicate[] predicates = conditions.toArray(new Predicate[conditions.size()]);
            return cb.and(predicates);
        };

        return dao().findAll(spec, PageUtil.cast(qry));
    }

}
