package com.zx.viwepagerforlunbo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zx.viwepagerforlunbo.R;

/**
 * Created by 陶玉亮 on 2015/4/16.
 *
 * <p>内容摘要：Application基类（对ImageLoader的初始化操作）</p>
 * <p>系统版本：V1.0.0</p>
 * <p>版权所有：宝润兴业</p>
 */
public class MyApplication extends Application {

	private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // SD缓存50MB
	private static final int LRU_MEMORY_CACHE = 4 * 1024 * 1024; // lru缓存4MB

	private Context context;

	private ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		initImageLoader(context);
	}


	/**
	 * 初始化ImageLoader
 	 */
	public void initImageLoader(Context context) {
		int threadPoolSize = 3;
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.ic_launcher)//默认加载前的图片
				.showImageForEmptyUri(R.mipmap.ic_launcher)//空Uri时显示的图片
				.showImageOnFail(R.mipmap.ic_launcher)
				.cacheOnDisk(true).cacheInMemory(true)
				.decodingOptions(new BitmapFactory.Options())
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.defaultDisplayImageOptions(defaultOptions)
				.threadPoolSize(threadPoolSize)
				.memoryCache(new WeakMemoryCache()).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	public Context getContext() {
		return context;
	}

}
