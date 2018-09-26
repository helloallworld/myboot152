package com.wumin.boot152.dubbo.zipkin.adapters;

import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.internal.Nullable;

import java.util.Collection;
import java.util.Collections;
/**
 * cr:client recevied 客户端收到返回埋点，span结束，
 */
public class DubboClientResponseAdapter implements ClientResponseAdapter {

    private StatusEnum status;

    public DubboClientResponseAdapter(@Nullable StatusEnum status) {
        this.status = status;
    }

    @Override
    public Collection responseAnnotations() {
        return Collections.singleton(KeyValueAnnotation.create(DubboTraceConst.STATUS_CODE, status.getDesc()));
    }
}