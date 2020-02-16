package com.github.rxyor.carp.ums.shared.common.uitl;

import com.github.rxyor.carp.ums.shared.common.core.consts.QryConst;
import com.github.rxyor.carp.ums.shared.common.core.model.PageQry;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 00:16:00
 * @since 1.0.0
 */
@UtilityClass
public class PageUtil {

    public static <T> Page<T> cast(Page page, List<T> elements) {
        if (elements == null) {
            elements = new ArrayList<>(0);
        }

        if (page == null) {
            Pageable pageable = PageRequest.of(QryConst.PAGE, QryConst.PAGE_SIZE);
            return new PageImpl<T>(elements, pageable, elements.size());
        }
        return new PageImpl<T>(elements, page.getPageable(), page.getTotalElements());
    }

    public static Pageable cast(PageQry qry) {
        if (qry != null) {
            return PageRequest.of(qry.getPage() - 1, qry.getPageSize());
        }
        return PageRequest.of(QryConst.PAGE - 1, QryConst.PAGE_SIZE);
    }

}
