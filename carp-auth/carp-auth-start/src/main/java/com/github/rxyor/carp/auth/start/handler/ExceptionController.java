package com.github.rxyor.carp.auth.start.handler;

import com.github.rxyor.carp.auth.common.enums.BizExCodeEnum;
import com.github.rxyor.carp.auth.security.exception.CarpOauth2Exception;
import com.github.rxyor.common.core.enums.CoreExCodeEnum;
import com.github.rxyor.common.core.model.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/2 周四 18:23:00
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Slf4j
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ExceptionController implements ErrorController {

    private final ServerProperties serverProperties;

    private final ErrorProperties errorProperties;

    private final List<ErrorViewResolver> errorViewResolvers;

    private final ErrorAttributes errorAttributes;

    public ExceptionController(ServerProperties serverProperties,
        List<ErrorViewResolver> errorViewResolvers,
        ErrorAttributes errorAttributes) {
        this.serverProperties = serverProperties;
        this.errorViewResolvers = errorViewResolvers;
        this.errorAttributes = errorAttributes;
        this.errorProperties = this.serverProperties.getError();
    }

    private List<ErrorViewResolver> sortErrorViewResolvers(List<ErrorViewResolver> resolvers) {
        List<ErrorViewResolver> sorted = new ArrayList<>();
        if (resolvers != null) {
            sorted.addAll(resolvers);
            AnnotationAwareOrderComparator.sortIfNecessary(sorted);
        }
        return sorted;
    }

    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    protected boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equalsIgnoreCase(parameter);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    protected ModelAndView resolveErrorView(HttpServletRequest request, HttpServletResponse response, HttpStatus status,
        Map<String, Object> model) {
        for (ErrorViewResolver resolver : this.errorViewResolvers) {
            ModelAndView modelAndView = resolver.resolveErrorView(request, status, model);
            if (modelAndView != null) {
                return modelAndView;
            }
        }
        return null;
    }

    @Override
    public String getErrorPath() {
        if (this.errorProperties == null) {
            return "";
        }
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
            .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }

    @RequestMapping
    @ResponseBody
    public R<Object> error(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        int statusCode = status.value();

        int code = 500;
        final int invalidAuthCode = CoreExCodeEnum.AUTHENTICATION_FAIL.getCode();
        //401响应码会导致浏览器弹出登录框
        if (statusCode == invalidAuthCode) {
            code = invalidAuthCode;
        }
        response.setStatus(200);

        Object e = request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
        if (e != null) {
            log.error("请求失败:", e);
            if (e instanceof UsernameNotFoundException
                || e instanceof BadCredentialsException
                || e instanceof InvalidGrantException) {
                return R.fail(BizExCodeEnum.USERNAME_PASSWORD_ERROR.getCode(),
                    "用户名或密码错误", ((Exception) e).getMessage());
            } else if (e instanceof DisabledException) {
                return R.fail(invalidAuthCode, "用户已被禁用", ((Exception) e).getMessage());
            } else if (e instanceof LockedException) {
                return R.fail(invalidAuthCode, "账户被锁定", ((Exception) e).getMessage());
            } else if (e instanceof AccountExpiredException) {
                return R.fail(invalidAuthCode, "账户过期", ((Exception) e).getMessage());
            } else if (e instanceof CredentialsExpiredException) {
                return R.fail(invalidAuthCode, "证书过期", ((Exception) e).getMessage());
            } else if (e instanceof InvalidTokenException) {
                return R.fail(BizExCodeEnum.LOGIN_TIMEOUT.getCode(),
                    "登录超时, 请重新登录", ((Exception) e).getMessage());
            } else if (e instanceof Throwable) {
                Throwable ex = (Throwable) e;
                while (ex != null && !(ex instanceof CarpOauth2Exception)) {
                    ex = ex.getCause();
                }
                if (ex != null && ex instanceof CarpOauth2Exception) {
                    CarpOauth2Exception ce = (CarpOauth2Exception) ex;
                    return R.fail(ce.getCode(), ce.getMsg());
                }
            }
            return R.fail(code, "请求失败", ((Exception) e).getMessage());
        }

        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        return R.fail(code, "请求失败", body);
    }

    /**
     * Determine if the stacktrace attribute should be included.
     * @param request the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
        IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    /**
     * Provide access to the error properties.
     * @return the error properties
     */
    protected ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }
}
