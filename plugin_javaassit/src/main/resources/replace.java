{
        List/*<Interceptor>*/ interceptors=new ArrayList/*<>*/();
        interceptors.addAll(client.interceptors());
        interceptors.add(retryAndFollowUpInterceptor);
        interceptors.add(new BridgeInterceptor(client.cookieJar()));
        interceptors.add(new CacheInterceptor(client.internalCache()));
        boolean add=false;
        for(int i=0;i<interceptors.size();i++){
            if(interceptors.get(i)instanceof LongChainMarkInterceptor){
                add=true;
                break;
            }
        }
        if(add){
            interceptors.add(new LongChainInterceptor());
        }
        interceptors.add(new ConnectInterceptor(client));
        if(!forWebSocket){
            for(int i=0;i<client.networkInterceptors().size();i++){
                if(client.networkInterceptors().get(i)instanceof LongChainInterceptor){
                    throw new IllegalArgumentException("LongChainInterceptor cannot add to networkInterceptor");
                }
            }
            interceptors.addAll(client.networkInterceptors());
        }
        interceptors.add(new CallServerInterceptor(forWebSocket));

        okhttp3.Interceptor.Chain chain=new RealInterceptorChain(interceptors,null,null,null,0,
        originalRequest,this,eventListener,client.connectTimeoutMillis(),
        client.readTimeoutMillis(),client.writeTimeoutMillis());

        return chain.proceed(originalRequest);
}
