package com.wumin.boot152.dubbo.zipkin.adapters;

import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerResponseAdapter;
import com.github.kristofa.brave.internal.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * ss: server send服务端处理结束埋点
 */
public class DubboServerResponseAdapter implements ServerResponseAdapter {

    private StatusEnum status;

    public DubboServerResponseAdapter(@Nullable StatusEnum status) {
        this.status = status;
    }

    @Override
    public Collection responseAnnotations() {
        return Collections.singleton(KeyValueAnnotation.create(DubboTraceConst.STATUS_CODE, status.getDesc()));
    }
}
