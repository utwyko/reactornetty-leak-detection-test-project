# Reactor Netty ByteBuf Leak

This application is leaking `ByteBuf` instances, which of course shouldn't happen. Netty's resource leak detector is enabled on the 'PARANOID' level to show this is happening.

To reproduce:

    ./gradlew clean test
    
The log output will contain messages like these:

    2017-07-03 14:14:42.234 ERROR 6609 --- [ntLoopGroup-2-7] io.netty.util.ResourceLeakDetector       : LEAK: ByteBuf.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.
    
Exceptions like these also occur:

    reactor.core.Exceptions$BubblingException: io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1