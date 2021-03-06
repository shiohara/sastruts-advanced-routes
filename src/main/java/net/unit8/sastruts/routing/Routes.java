package net.unit8.sastruts.routing;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.unit8.sastruts.routing.detector.ClassControllerDetector;
import net.unit8.sastruts.routing.detector.ComponentControllerDetector;

import org.seasar.framework.container.hotdeploy.HotdeployUtil;

public class Routes {
	private static List<String> possibleControllers = null;
	private static RouteSet routeSet;

	public static final List<String> HTTP_METHODS = Collections.unmodifiableList(
			Arrays.asList(new String[]{"GET" , "HEAD", "POST", "PUT", "DELETE", "OPTIONS"}));


	public static String generate(Options options) {
		return getRouteSet().generate(options);
	}

	public static Options recognizePath(String path) {
		return getRouteSet().recognizePath(path);
	}

	public static void load(File config) {
		getRouteSet().getConfigurationFiles().clear();
		getRouteSet().addConfigurationFile(config);
		getRouteSet().load();
	}

	public static synchronized RouteSet getRouteSet() {
		if (routeSet == null)
			routeSet = new RouteSet();
		return routeSet;
	}

	public static synchronized List<String> possibleControllers() {
		if (possibleControllers == null) {
			ControllerDetector detector = HotdeployUtil.isHotdeploy() ? new ClassControllerDetector() : new ComponentControllerDetector();
			possibleControllers = detector.detect();
		}
		return possibleControllers;
	}

}
