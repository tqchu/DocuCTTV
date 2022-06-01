package com.ctvv.util;

import com.ctvv.model.RevenueImportGraphPoint;
import com.google.gson.Gson;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class GsonUtil {
	public static String getAmount(
			List<RevenueImportGraphPoint> pointList, int start, int end,
			Function<RevenueImportGraphPoint, Long> func) {
		Gson gsonObj = new Gson();
		Map<Object, Object> map ;
		List<Map<Object, Object>> list = new ArrayList<>();
		int difference = start - 1;
		for (int i = start; i <= end; i++) {
			map = new HashMap<>();
			if ((i - difference - 1) >= pointList.size()) {
				// Nếu hết ds Point thì cho các điểm còn lại có giá trị 0
				for (; i <= end; i++) {
					map.put("label", i + "");
					map.put("y", 0);
					list.add(map);
				}
			} else {
				RevenueImportGraphPoint point = pointList.get(i - difference - 1);
				map.put("label", i + "");
				int positionOfPoint = Integer.parseInt(point.getLabel());
				// Dòng này sử dụng Functional Interface (func.apply())
				if (positionOfPoint == i) {
					map.put("y", func.apply(point));
				} else {
					difference++;
					map.put("y", 0);
				}
				list.add(map);
			}
		}

		return gsonObj.toJson(list);
	}

	public static String getOrderElementList(Map<String, Integer> orderChartElementList) {
		Gson gsonObj = new Gson();
		Map<Object, Object> map;
		List<Map<Object, Object>> list = new ArrayList<>();
		Set<Map.Entry<String, Integer>> entrySet = orderChartElementList.entrySet();
		Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			map = new HashMap<>();
			Map.Entry<String, Integer> element = iterator.next();
			map.put("label", element.getKey());
			map.put("y", element.getValue());
			list.add(map);
		}
		return gsonObj.toJson(list);
	}
}
