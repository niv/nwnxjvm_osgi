package es.elv.nwnx2.jvm.osgi;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

public class FelixLauncher {
	private static HostActivator m_activator;
	private static Felix m_felix;

	public static void setup() {
		System.out.println("Configuring felix ..");

		try {
			m_activator = new HostActivator();
			List<BundleActivator> list = new ArrayList<BundleActivator>();
			list.add(m_activator);
			list.add(new org.apache.felix.eventadmin.impl.Activator());

			Properties configMap = new Properties();

			configMap.put("felix.systembundle.activators", list);
			configMap.put("org.apache.felix.eventadmin.Timeout", "0");

			configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
				"org.nwnx.nwnx2.jvm; version=1.0.0," +
				"org.nwnx.nwnx2.jvm.constants; version=1.0.0," +
				"org.apache.felix.eventadmin; version=1.0.0," +
				"org.osgi.service.event; version=1.3.0," +
				"org.osgi.service.log; version=1.3.0"
			);

			configMap.load(new FileInputStream(new File("felix.properties")));

			m_felix = new Felix(configMap);
			m_felix.init();

			AutoProcessor.process(configMap, m_felix.getBundleContext());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Could not create framework: " + ex);
		}
	}

	public static void init() {
		System.out.println("Starting felix .. :)");
		try {
			m_felix.start();
		} catch (BundleException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot start felix.");
		}
	}

	public static void shutdown() {
		System.out.println("Shutting down felix ..");
		try {
			m_felix.stop();
			m_felix.waitForStop(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
