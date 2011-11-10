package es.elv.nwnx2.jvm.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


class HostActivator implements BundleActivator {
	private BundleContext m_context = null;

	public BundleContext getContext() {
		return m_context;
	}

	public void start(BundleContext context) {
		m_context = context;
	}

	public void stop(BundleContext context) {
	}

	public Bundle[] getBundles() {
		if (m_context != null) {
			return m_context.getBundles();
		}
		return null;
	}
}
