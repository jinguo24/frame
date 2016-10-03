package com.simple.common.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyClassUtils {

	public static List<Class<?>> getAllAssignedClass(Class<?> cls, String basePackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> c : getClasses(basePackage)) {
			if (cls.isAssignableFrom(c) && !cls.equals(c)) {
				classes.add(c);
			}
		}
		return classes;
	}

	public static List<Class<?>> getClasses(String basePackage) {
		String path = basePackage.replace('.', '/');
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL url = classloader.getResource(path);
		File file = null;
		if (null != url) {
			file = new File(url.getFile());
		}
		return getClasses(file, basePackage);
	}

	private static List<Class<?>> getClasses(File dir, String currentPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (null == dir || !dir.exists()) {
			return classes;
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				classes.addAll(getClasses(f, currentPackage + "." + f.getName()));
			}
			String name = f.getName();
			if (name.endsWith(".class")) {
				try {
					classes.add(Class.forName(currentPackage + "." + name.substring(0, name.length() - 6)));
				} catch (ClassNotFoundException e) {
				}
			}
		}
		return classes;
	}
}
