package com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.criteria;

import com.github.rxyor.carp.query.qry.kvconfig.KvConfigQry;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dataobj.KvConfigDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dao.KvConfigDAO;
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
public class KvConfigCriteria {

    private final KvConfigDAO kvConfigDAO;

    public KvConfigDAO dao() {
        return kvConfigDAO;
    }

    public Page<KvConfigDO> selectPage(KvConfigQry qry) {
        Preconditions.checkArgument(qry != null,
            "查询参数不能为空");

        Specification<KvConfigDO> spec = (Specification<KvConfigDO>) (root, query, cb) -> {
            List<Predicate> conditions = new ArrayList<>(4);
            if (qry.getId() != null) {
                conditions.add(cb.equal(root.get("id"), qry.getId()));
            }
            if (StringUtils.isNotBlank(qry.getAppId())) {
                conditions.add(cb.equal(root.get("appId"), qry.getAppId()));
            }
            if (StringUtils.isNotBlank(qry.getKey())) {
                conditions.add(cb.like(root.get("key"), SqlUtil.allLike(qry.getKey())));
            }
            if (StringUtils.isNotBlank(qry.getValue())) {
                conditions.add(cb.like(root.get("kvConfigCode"), SqlUtil.allLike(qry.getValue())));
            }

            Predicate[] predicates = conditions.toArray(new Predicate[conditions.size()]);
            return cb.and(predicates);
        };

        return dao().findAll(spec, PageUtil.cast(qry));
    }

}
