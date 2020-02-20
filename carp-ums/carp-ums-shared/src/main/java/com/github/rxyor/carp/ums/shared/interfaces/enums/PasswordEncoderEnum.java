package com.github.rxyor.carp.ums.shared.interfaces.enums;

import com.github.rxyor.common.core.enums.KeyValue;
import lombok.Getter;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 15:45:00
 * @since 1.0.0
 */
@ToString
public enum PasswordEncoderEnum implements KeyValue<String> {

    NOOP("{noop}", "noop"),
    BCRYPT("{bcrypt}", "bcrypt"),
    LDAP("ldap", "ldap"),
    MD4("{MD4}", "MD4"),
    MD5("{MD5}", "MD5"),
    PBKDF2("{pbkdf2}", "pbkdf2"),
    SHA_1("{SHA-1}", "SHA-1"),
    SHA_256("{SHA-256}", "SHA-256"),
    SHA256("{sha256}", "sha256"),
    ARGON2("{argon2}", "argon2"),
    ;

    @Getter
    private String code;

    @Getter
    private String desc;

    PasswordEncoderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
