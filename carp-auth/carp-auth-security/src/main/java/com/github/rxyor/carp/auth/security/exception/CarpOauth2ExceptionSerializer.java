package com.github.rxyor.carp.auth.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import lombok.EqualsAndHashCode;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 19:28:00
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
public class CarpOauth2ExceptionSerializer extends StdSerializer<CarpOauth2Exception> {

    protected CarpOauth2ExceptionSerializer() {
        super(CarpOauth2Exception.class);
    }


    @Override
    public void serialize(CarpOauth2Exception e, JsonGenerator generator,
        SerializerProvider serializerProvider) throws IOException {
        final String code = "code";
        final String msg = "msg";
        final String data = "data";

        generator.writeStartObject();
        generator.writeObjectField(code, e.getHttpErrorCode());
        generator.writeObjectField(msg, e.getMessage());
        generator.writeObjectField(data, e.getCode());
        generator.writeEndObject();
    }
}
