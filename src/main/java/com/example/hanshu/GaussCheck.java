package com.example.hanshu;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaussCheck {
	private static final Logger logger = LoggerFactory.getLogger(GaussCheck.class);

	private boolean isGauss;

	private StatisticConstant statisticConstant;

	public GaussCheck(boolean isGauss, StatisticConstant statisticConstant) {
		this.isGauss = isGauss;
		this.statisticConstant = statisticConstant;
	}

	public GaussCheck() {
	}

	public StatisticConstant getStatisticConstant() {
		return statisticConstant;
	}

	public void setStatisticConstant(StatisticConstant statisticConstant) {
		this.statisticConstant = statisticConstant;
	}

	public boolean isGauss() {
		return isGauss;
	}

	public void setGauss(boolean gauss) {
		isGauss = gauss;
	}

	public static GaussCheck KSTest(Double[] list) {
		logger.info("***********************ks-test**********************");

		if (list == null || list.length == 0) {
			return new GaussCheck(false, new StatisticConstant());
		}

		int length = list.length; /// TODO 原始数组长度
		Double median = 0.0D; // 中位数
		Double upperQuartile = list[length * 3 / 4]; // 上四分位
		Double downQuartile = list[length / 4]; // 下四分位
		if (length % 2 == 0) {
			Double midTemp = list[length / 2 - 1] + list[length / 2];
			median = midTemp / 2.0;
		} else {
			median = list[length / 2];
		}

		TreeMap<Double, Integer> map = new TreeMap<>(); /// 有序map,key-原始值,value-频数

		Double sum = 0.00;
		for (int i = 0; i < length; i++) {
			sum += list[i]; // 求和
			Integer count = map.get(list[i]);
			if (count != null && count != 0) {
				map.put(list[i], ++count);
			} else {
				map.put(list[i], 1);
			}
		}

		Double avgNum = sum / length * 1.00;

		Double s = 0.00; // 差平方和
		for (int i = 0; i < length; i++) {
			Double temp = (list[i] - avgNum) * (list[i] - avgNum);
			s = s + temp;
		}
		Double var = s / (length - 1); // 方差
		Double deviation = Math.sqrt(var); // 标准差

		Double variation = deviation / avgNum; // 变异系数

		int mapSize = map.size();
		Double[] keyArray = map.keySet().toArray(new Double[] {});

		Double maxNum = keyArray[mapSize - 1]; // 最大值
		Double minNum = keyArray[0]; // 最小值
		Double range = maxNum - minNum; // 极差
		Double modeNum = 0.0;

		Integer[] frequencyArr = map.values().toArray(new Integer[] {}); // 频数数组

		map.clear();

		int frequencyArrLength = keyArray.length;
		Double[] leijiArr = new Double[frequencyArrLength]; // TODO 实际累计概率数组
															// Cumulative
															// probability

		int modeIndex = 0;
		for (int i = 0; i < frequencyArr.length; i++) {
			if (frequencyArr[modeIndex] < frequencyArr[i]) {
				modeIndex = i;
			}
			Integer temp = CumulativeFrequency(frequencyArr, i); // 累计频数
			Double num = temp / (length * 1.00); // 累计频率
			leijiArr[i] = num;
		}
		modeNum = keyArray[modeIndex];

		Double[] gaussArr = new Double[frequencyArrLength]; // TODO 理论累计概率
		Double maxDeviation = 0.00; // 理论值与实际值最大偏差

		for (int i = 0; i < frequencyArrLength; i++) {
			Double b = (keyArray[i] - avgNum) / deviation; // 标准正态分布离差
			gaussArr[i] = Gauss(b); // 理论概率密度

			Double temp1 = Math.abs(gaussArr[i] - leijiArr[i]);
			if (maxDeviation < temp1) {
				maxDeviation = temp1;
			}
		}

		Double d = getD(length);
		boolean isGauss = checkGauss(d, maxDeviation);

		// 统计常量
		StatisticConstant statisticConstant = new StatisticConstant(avgNum, median, modeNum, upperQuartile,
				downQuartile, var, deviation, variation, range, maxNum, minNum);

		return new GaussCheck(isGauss, statisticConstant);
	}

	public static Map drawData(Double u, Double sima) {
		Double t = u - 3 * sima;
		TreeMap<Double, Double> map = new TreeMap<>();
		// 为了画出峰值，绘图区间为[u-3*sima,u+3*sima],步长为u/10
		for (; t < u + 3 * sima; t += u / 10) {
			Double y = DensityFunc(t, u, sima);
			map.put(t, y);
		}

		Double[] x = map.keySet().toArray(new Double[] {});
		Double[] y = map.values().toArray(new Double[] {});

		map.clear();

		Map dataMap = new HashMap();
		dataMap.put("x", x); // 横轴数组
		dataMap.put("y", y); // 纵轴数组

		return dataMap;
	}

	/**
	 * 默认置信度：a=0.05
	 *
	 * @param d
	 * @param maxDeviation
	 * @return
	 */
	private static boolean checkGauss(Double d, Double maxDeviation) {
		if (maxDeviation >= d)
			return false; // 拒绝原假设 p< 0.05
		else
			return true; // 接受原假设 ---服从正态分布 p> 0.05

	}

	/**
	 * 默认置信度：a=0.05
	 *
	 * @param n
	 *            样本数量
	 * @return
	 */
	private static Double getD(Integer n) {
		if (n > 0 && n <= 5) {
			return 0.562;
		} else if (n > 5 && n <= 10) {
			return 0.409;
		} else if (n > 10 && n <= 20) {
			return 0.294;
		} else if (n > 20 && n <= 30) {
			return 0.242;
		} else if (n > 30 && n <= 50) {
			return 0.189;
		} else if (n > 50) {
			return 1.36 / Math.sqrt(n);
		}
		return -0.1;
	}

	/**
	 * 正态分布概率密度
	 *
	 * @param b
	 * @return
	 */
	private static Double Gauss(Double b) {
		Double a = -10000D;
		Double sum = 0.00;
		Integer n = 10000;
		for (int i = 1; i < n; i++) {
			double t = temp(a, b, n, i);
			sum += f(t);
		}
		return (2 * sum + f(a) + f(b)) * (b - a) / (2 * n); // 梯形面积
	}

	/**
	 * 区间的中间值
	 *
	 * @param a
	 *            下限
	 * @param b
	 *            上限
	 * @param n
	 *            等分大小
	 * @param i
	 *            第几区间
	 * @return
	 */
	private static Double temp(Double a, Double b, Integer n, Integer i) {
		return a + i * (b - a) / n;
		// return a + (2 * i - 1) * (b - a) /(2 * n);
	}

	/**
	 * 正态分布被积函数
	 *
	 * @param t
	 * @return
	 */
	private static Double f(double t) {
		return 1 / Math.sqrt(2 * Math.PI) * Math.exp(-t * t / 2);
	}

	/**
	 * 正态分布概率密度函数
	 *
	 * @param x
	 *            因变量
	 * @param u
	 *            均值（数学期望）
	 * @param sigma
	 *            标准差
	 * @return
	 */
	private static Double DensityFunc(Double x, Double u, Double sigma) {
		return 1.0 / (Math.sqrt(2 * Math.PI) * sigma) * Math.exp(-(x - u) * (x - u) / (2 * sigma * sigma));
	}

	/**
	 * 计算累计频数
	 */
	private static Integer CumulativeFrequency(Integer[] arr, int n) {
		if (n == 0)
			return arr[0];
		else {
			return CumulativeFrequency(arr, n - 1) + arr[n];
		}
	}
}
