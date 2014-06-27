package com.legoo.haier.Application;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @class Hospital ThreadPool
 * @author LeonNoW
 * @version 1.0
 * @date 2014-02-08
 */
public class HaierThreadPool
{
	private static final int NETWORK_CORE_POOL_SIZE = 10;
	private static final int NETWORK_MAXIMUM_POOL_SIZE = 128;
	private static final int NETWORK_KEEP_ALIVE_TIME = 1;
	private static BlockingQueue<Runnable> NETWORK_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(NETWORK_MAXIMUM_POOL_SIZE);
	public static Executor NETWORK_EXECUTOR = new ThreadPoolExecutor(NETWORK_CORE_POOL_SIZE, NETWORK_MAXIMUM_POOL_SIZE, NETWORK_KEEP_ALIVE_TIME, TimeUnit.SECONDS, NETWORK_WORK_QUEUE);
	
	private static final int SAMPLE_CORE_POOL_SIZE = 6;
	private static final int SAMPLE_MAXIMUM_POOL_SIZE = 128;
	private static final int SAMPLE_KEEP_ALIVE_TIME = 1;
	private static BlockingQueue<Runnable> SAMPLE_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(SAMPLE_MAXIMUM_POOL_SIZE);
	public static Executor SAMPLE_EXECUTOR = new ThreadPoolExecutor(SAMPLE_CORE_POOL_SIZE, SAMPLE_MAXIMUM_POOL_SIZE, SAMPLE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, SAMPLE_WORK_QUEUE);
	
	private static final int IMAGE_CORE_POOL_SIZE = 10;
	private static final int IMAGE_MAXIMUM_POOL_SIZE = 128;
	private static final int IMAGE_KEEP_ALIVE_TIME = 3;
	private static BlockingQueue<Runnable> IMAGE_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(IMAGE_MAXIMUM_POOL_SIZE);
	public static Executor IMAGE_EXECUTOR = new ThreadPoolExecutor(IMAGE_CORE_POOL_SIZE, IMAGE_MAXIMUM_POOL_SIZE, IMAGE_KEEP_ALIVE_TIME, TimeUnit.SECONDS, IMAGE_WORK_QUEUE);
}
