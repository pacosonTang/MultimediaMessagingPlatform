package com.myplugin;

import java.io.File;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Packet;

// 如果需要拦截消息，就必须实现  PacketInterceptor
public class PluginDemo implements PacketInterceptor, Plugin{
	private static PluginManager pluginManager;
	private InterceptorManager interceptorManager;
	
	public PluginDemo() {
		interceptorManager = InterceptorManager.getInstance();
	}
	
	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {
		System.out.println("拦截消息以进行逻辑处理" + "from =" + packet.getFrom() + 
				", to = " + packet.getTo() + 
				", incoming = " + incoming + 
				", proccessed = " + processed);
		System.out.println("\n=========================");
	}
	
	@Override
	public void destroyPlugin() {
		interceptorManager.removeInterceptor(this);
		System.out.println("PluginDemo Plugin has been destroyed.");
	}

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		interceptorManager.addInterceptor(this);
		pluginManager = manager;			
		System.out.println("PluginDemo Plugin has been installed successfully.");
	}
}
