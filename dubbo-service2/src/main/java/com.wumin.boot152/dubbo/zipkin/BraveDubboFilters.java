package com.wumin.boot152.dubbo.zipkin;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.*;
import com.wumin.boot152.dubbo.zipkin.adapters.*;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class BraveDubboFilters implements com.alibaba.dubbo.rpc.Filter{
    /**
     * @tips:这里不要用注解的方式
     */
    private ClientRequestInterceptor clientRequestInterceptor;
    private ClientResponseInterceptor clientResponseInterceptor;
    private ServerRequestInterceptor serverRequestInterceptor;
    private ServerResponseInterceptor serverResponseInterceptor;

    public void setClientRequestInterceptor(ClientRequestInterceptor clientRequestInterceptor) {
        this.clientRequestInterceptor = clientRequestInterceptor;
    }

    public void setClientResponseInterceptor(ClientResponseInterceptor clientResponseInterceptor) {
        this.clientResponseInterceptor = clientResponseInterceptor;
    }

    public void setServerRequestInterceptor(ServerRequestInterceptor serverRequestInterceptor) {
        this.serverRequestInterceptor = serverRequestInterceptor;
    }

    public void setServerResponseInterceptor(ServerResponseInterceptor serverResponseInterceptor) {
        this.serverResponseInterceptor = serverResponseInterceptor;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        /*
         * 监控的 dubbo 服务,不纳入跟踪范围
         */
        if ("com.alibaba.dubbo.monitor.MonitorService".equals(invoker.getInterface().getName())) {
            return invoker.invoke(invocation);
        }
        RpcContext context = RpcContext.getContext();
        //设置才采集率
        context.setAttachment(DubboTraceConst.SAMPLED,"1");
        /*
         * 调用的方法名 以此作为 span name
         */
        String methodName = invocation.getMethodName();
        /*
         * provider 应用相关信息
         */
        StatusEnum status = StatusEnum.OK;
        if ("0".equals(invocation.getAttachment(DubboTraceConst.SAMPLED))
                || "false".equals(invocation.getAttachment(DubboTraceConst.SAMPLED))) {
            return invoker.invoke(invocation);
        }
        //注入
        if (!inject()) {
            return invoker.invoke(invocation);
        }
        if (context.isConsumerSide()) {
            System.out.println("consumer execute");
            clientRequestInterceptor.handle(new DubboClientRequestAdapter(invocation.getAttachments(), methodName));
            Result result = null;
            try {
                result = invoker.invoke(invocation);
            } catch (RpcException e) {
                status = StatusEnum.ERROR;
                throw e;
            } finally {
                final DubboClientResponseAdapter clientResponseAdapter = new DubboClientResponseAdapter(status);
                clientResponseInterceptor.handle(clientResponseAdapter);
            }
            return result;
        } else if (context.isProviderSide()) {
            System.out.println("provider execute");
            serverRequestInterceptor.handle(new DubboServerRequestAdapter(context.getAttachments(), methodName));
            Result result = null;
            try {
                result = invoker.invoke(invocation);
            } finally {
                serverResponseInterceptor.handle(new DubboServerResponseAdapter(status));
            }
            return result;
        }
        return invoker.invoke(invocation);
    }

    private boolean inject() {
        Brave brave = (Brave) ApplicationContextHolder.getBean(Brave.class);
        if (brave == null) {
            return false;
        }
        this.setClientRequestInterceptor(brave.clientRequestInterceptor());
        this.setClientResponseInterceptor(brave.clientResponseInterceptor());
        this.setServerRequestInterceptor(brave.serverRequestInterceptor());
        this.setServerResponseInterceptor(brave.serverResponseInterceptor());
        return true;
    }
}
