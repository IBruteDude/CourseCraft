package com.coursecraft.utils;

import java.lang.reflect.Field;
import java.util.List;

public class ToStringGenerator {
	private List<Field> fieldList;
	private Class<?> storedClass;

	public ToStringGenerator(Class<?> clazz) {
		storedClass = clazz;
		while (clazz != null) {
			fieldList.addAll(List.of(clazz.getFields()));
			clazz = clazz.getSuperclass();
		}
	}

	public String fieldListString() {
		return List.of(storedClass).stream().map(field -> {
			return field.toGenericString();
		}).toList().toString();
	}
}
