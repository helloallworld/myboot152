package com.wumin.boot152.dubbo.zipkin.adapters;

public interface DubboTraceConst {
    String SAMPLED = "dubbo.trace.sampled";
    String PARENT_SPAN_ID = "dubbo.trace.parentSpanId";
    String SPAN_ID = "dubbo.trace.spanId";
    String TRACE_ID = "dubbo.trace.traceId";
    String STATUS_CODE = "dubbo.trace.staus_code";
}