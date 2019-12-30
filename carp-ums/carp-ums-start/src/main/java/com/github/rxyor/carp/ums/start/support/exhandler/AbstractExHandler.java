package com.github.rxyor.carp.ums.start.support.exhandler;

import com.github.rxyor.common.core.enums.CoreExCodeEnum;
import com.github.rxyor.common.core.enums.KeyValue;
import com.github.rxyor.common.core.exception.CoreException;
import com.github.rxyor.common.core.exception.IllegalParamException;
import com.github.rxyor.common.core.exception.RpcInvokeException;
import com.github.rxyor.common.support.enums.MysqlErrorCodeEnum;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Joiner;
import java.sql.SQLException;
import java.util.Optional;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/11/28 周四 11:08:00
 * @since 1.0.0
 */
public abstract class AbstractExHandler<R> {

    /**
     * Throwable
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public R throwable(Throwable e) {
        return processCommonException(e);
    }

    /**
     * Exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e) {
        return processCommonException(e);
    }

    /**
     * CoreException：业务系统自定义顶级运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CoreException.class)
    @ResponseBody
    public R coreException(CoreException e) {
        return processCommonException(e);
    }

    /**
     * jdk内置参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R illegalArgumentException(IllegalArgumentException e) {
        return processCommonException(new IllegalParamException(
            CoreExCodeEnum.ILLEGAL_PARAM.getCode(), e.getMessage(), e));
    }

    /**
     * mvc 参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseBody
    public R httpMessageConversionException(HttpMessageConversionException e) {
        String msg = "参数缺失或参数格式错误";
        return illegalArgumentException(new IllegalArgumentException(msg, e));
    }

    /**
     * hibernate validator校验失败异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R constraintViolationException(ConstraintViolationException e) {
        String msg = Joiner.on(";").join(HibValidatorHelper.parseException(e));
        return illegalArgumentException(new IllegalArgumentException(msg, e));
    }


    /**
     * spring dao：数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public R dataIntegrityViolationException(DataIntegrityViolationException e) {
        return processMysqlException(e);
    }

    /**
     * RPC 接口调用异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(RpcInvokeException.class)
    @ResponseBody
    public R rpcInvokeException(RpcInvokeException e) {
        return processRpcInvokeException(e);
    }

    /**
     * mybatis：数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public R persistenceException(PersistenceException e) {
        return processMysqlException(e);
    }

    protected Class<? extends KeyValue> getMysqlErrorCodeEnumType() {
        return MysqlErrorCodeEnum.class;
    }

    /**
     * 注意FriendlyTipEnum最好为5位以上Integer类型数字，避免与CoreExCodeEnum
     * 中枚举编码冲突
     *
     * @return
     */
    protected abstract Class<? extends KeyValue> getFriendlyTipEnumType();

    /**
     * 将异常信息转换为Http JSON响应体，如Result{code:200,success:true,data:...,tracedId:uiyi8}
     *
     * @param wrapper
     * @return
     */
    protected abstract R convert2R(ExceptionWrapper wrapper);

    /**
     * 处理通用异常
     *
     * @param e
     * @return
     */
    protected R processCommonException(Throwable e) {
        Integer code = CoreExCodeEnum.FAIL.getCode();
        String msg = CoreExCodeEnum.FAIL.getDesc();
        if (e != null) {
            if (e instanceof CoreException) {
                code = ((CoreException) e).getCode();
                msg = ((CoreException) e).getMsg();
            } else {
                msg = e.getMessage();
            }
        }
        return replaceFriendlyTipAndConvert2R(e, code, msg);
    }

    /**
     * 处理RPC调用异常
     *
     * @param e
     * @return
     */
    protected R processRpcInvokeException(Throwable e) {
        Integer code = CoreExCodeEnum.RPC_FAIL.getCode();
        String msg = CoreExCodeEnum.RPC_FAIL.getDesc();
        return replaceFriendlyTipAndConvert2R(e, code, msg);
    }

    /**
     * 处理mysql异常
     *
     * @param e
     * @return
     */
    protected R processMysqlException(Exception e) {
        Integer code = CoreExCodeEnum.MYSQL_FAIL.getCode();
        String msg = CoreExCodeEnum.MYSQL_FAIL.getDesc();
        Integer vendorErrorCode = this.loopFindVendorErrorCode(e);
        Class clazz = this.getMysqlErrorCodeEnumType();
        if (vendorErrorCode != null && clazz != null) {
            msg = Optional.ofNullable(KeyValue.Util
                .getDescByCode(vendorErrorCode, clazz))
                .orElse(CoreExCodeEnum.MYSQL_FAIL.getDesc());
        }
        return replaceFriendlyTipAndConvert2R(e, code, msg);
    }

    /**
     * 循环遍历异常堆栈，找出MysqlDataTruncation中对应mysql执行异常码
     *
     * @param e
     * @return
     */
    protected Integer loopFindVendorErrorCode(Throwable e) {
        while (e != null) {
            if (isSqlException(e)) {
                break;
            }
            e = e.getCause();
        }
        if (e != null && isSqlException(e)) {
            SQLException sqlException = (SQLException) e;
            return sqlException.getErrorCode();
        }
        return null;
    }

    private boolean isSqlException(Throwable e) {
        if (e == null) {
            return false;
        }
        return e instanceof SQLException;
    }

    private R replaceFriendlyTipAndConvert2R(Throwable e, Integer code, String msg) {
        ExceptionWrapper wrapper = new ExceptionWrapper(code, msg, e);
        this.replaceFriendlyMsg(wrapper);
        return convert2R(wrapper);
    }

    private void replaceFriendlyMsg(ExceptionWrapper wrapper) {
        Class clazz = getFriendlyTipEnumType();
        if (clazz == null || wrapper == null || wrapper.getCode() == null) {
            return;
        }
        String msg = KeyValue.Util.getDescByCode(wrapper.getCode(), clazz);
        if (StringUtils.isNotBlank(msg)) {
            wrapper.setMsg(msg);
        }
    }

}
