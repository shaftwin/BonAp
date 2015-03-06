package com.example.shaft.bonap;

import android.app.Application;

import com.iainconnor.objectcache.DiskCache;

import java.io.File;
import java.io.IOException;


/**
 * Created by Shaft on 05/03/2015.
 */

public class MyApplication extends Application {

    private DiskCache mDiskCache;

    public DiskCache getDiskCache() {
        if (mDiskCache == null) {
            final String cachePath = getCacheDir().getPath();
            final File cacheFile = new File(cachePath + File.separator + BuildConfig.APPLICATION_ID);

            try {
                mDiskCache = new DiskCache(cacheFile, BuildConfig.VERSION_CODE, 1024 * 1024 * 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mDiskCache;
    }

    public void clearCache() {
        try {
            mDiskCache.clearCache();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
