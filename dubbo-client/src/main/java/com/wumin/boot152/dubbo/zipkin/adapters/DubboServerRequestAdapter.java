package com.wumin.boot152.dubbo.zipkin.adapters;

import com.github.kristofa.brave.ServerRequestAdapter;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.TraceData;
import com.github.kristofa.brave.internal.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.github.kristofa.brave.IdConversion.convertToLong;

/**
 * sr:server recevied 服务端收到请求埋点
 */
public class DubboServerRequestAdapter implements ServerRequestAdapter {

    private Map headers;

    private String spanName;

    public DubboServerRequestAdapter(@Nullable Map headers, @Nullable String spanName) {
        this.headers = headers;
        this.spanName = spanName;
    }

    @Override
    public TraceData getTraceData() {
        final Object sampled = headers.get(DubboTraceConst.SAMPLED);
        if (sampled != null) {
            if ("0".equals(sampled) || "false".equals(sampled.toString().toLowerCase())) {
                return TraceData.builder().sample(false).build();
            } else {
                final String parentSpanId = headers.get(DubboTraceConst.PARENT_SPAN_ID)==null?null:headers.get(DubboTraceConst.PARENT_SPAN_ID).toString();
                final String traceId = headers.get(DubboTraceConst.TRACE_ID)==null?null:headers.get(DubboTraceConst.TRACE_ID).toString();
                final String spanId = headers.get(DubboTraceConst.SPAN_ID)==null?null:headers.get(DubboTraceConst.SPAN_ID).toString();
                if ( traceId != null && spanId != null){
                    SpanId span = getSpanId(traceId, spanId, parentSpanId);
                    return TraceData.builder().sample(true).spanId(span).build();
                }
            }
        }
        return TraceData.builder().build();
    }

    @Override
    public String getSpanName() {
        return this.spanName;
    }

    @Override
    public Collection requestAnnotations() {
        return Collections.emptyList();
    }

    static SpanId getSpanId(String traceId, String spanId, String parentSpanId) {
        return SpanId.builder().traceId(convertToLong(traceId)).spanId(convertToLong(spanId))
                .parentId(parentSpanId == null ? null : convertToLong(parentSpanId)).build();
    }
}
