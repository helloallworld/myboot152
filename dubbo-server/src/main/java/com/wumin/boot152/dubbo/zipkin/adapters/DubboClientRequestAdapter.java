package com.wumin.boot152.dubbo.zipkin.adapters;

import brave.internal.Nullable;
import com.alibaba.dubbo.rpc.RpcContext;
import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.IdConversion;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.twitter.zipkin.gen.Endpoint;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * cs: client start 客户端发起请求埋点
 */
public class DubboClientRequestAdapter implements ClientRequestAdapter {

    private Map headers;
    private String spanName;

    public DubboClientRequestAdapter(@Nullable Map headers, @Nullable String spanName) {
        this.headers = headers;
        this.spanName = spanName;
    }

    @Override
    public String getSpanName() {
        return this.spanName;
    }

    @Override
    public void addSpanIdToRequest(SpanId spanId) {
        if (spanId == null) {
            headers.put(DubboTraceConst.SAMPLED, "0");
        } else {
            headers.put(DubboTraceConst.SAMPLED, "1");
            headers.put(DubboTraceConst.TRACE_ID, IdConversion.convertToString(spanId.traceId));
            headers.put(DubboTraceConst.SPAN_ID, IdConversion.convertToString(spanId.spanId));
            if (spanId.nullableParentId() != null) {
                headers.put(DubboTraceConst.PARENT_SPAN_ID, IdConversion.convertToString(spanId.parentId));
            }
        }
    }

    @Override
    public Collection requestAnnotations() {
        return Collections.emptyList();
//        return Collections.singletonList(KeyValueAnnotation.create("url", RpcContext.getContext().getUrl().toString()));

    }

    @Override
    public Endpoint serverAddress() {
        return null;
    }
}
