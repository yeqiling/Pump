package com.huxq17.download.config;

import android.content.Context;

import com.huxq17.download.PumpFactory;
import com.huxq17.download.core.DownloadInterceptor;
import com.huxq17.download.core.connection.DownloadConnection;

import java.util.ArrayList;
import java.util.List;

public class DownloadConfig {
    /**
     * 允许同时下载的最大任务数量
     */
    private int maxRunningTaskNumber = 3;
    /**
     * 最小可用的内存空间
     */
    private long minUsableStorageSpace = 4 * 1024L;
    private List<DownloadInterceptor> interceptors = new ArrayList<>();

    private DownloadConfig() {
    }

    public int getMaxRunningTaskNumber() {
        return maxRunningTaskNumber;
    }

    public long getMinUsableSpace() {
        return minUsableStorageSpace;
    }

    public static Builder newBuilder(Context context) {
        return new Builder();
    }

    public void addDownloadInterceptor(DownloadInterceptor downloadInterceptor) {
        interceptors.add(downloadInterceptor);
    }

    public List<DownloadInterceptor> getInterceptors() {
        return interceptors;
    }

    public static class Builder {
        private DownloadConfig downloadConfig;

        private Builder() {
            this.downloadConfig = new DownloadConfig();
        }

        /**
         * Set the maximum number of tasks to run, default 3.
         *
         * @param maxRunningTaskNumber maximum number of tasks to run
         * @return
         */
        public Builder setMaxRunningTaskNum(int maxRunningTaskNumber) {
            downloadConfig.maxRunningTaskNumber = maxRunningTaskNumber;
            return this;
        }

        /**
         * Set the minimum available storage space size for downloading to avoid insufficient storage space during downloading, default is 4kb。
         *
         * @param minUsableStorageSpace minimum available storage space size
         * @return
         */
        public Builder setMinUsableStorageSpace(long minUsableStorageSpace) {
            downloadConfig.minUsableStorageSpace = minUsableStorageSpace;
            return this;
        }

        public Builder addDownloadInterceptor(DownloadInterceptor interceptor) {
            downloadConfig.interceptors.add(interceptor);
            return this;
        }

        public Builder setDownloadConnectionFactory(DownloadConnection.Factory factory) {
            PumpFactory.getService(IDownloadConfigService.class).setDownloadConnectionFactory(factory);
            return this;
        }

        public void build() {
            PumpFactory.getService(IDownloadConfigService.class).setConfig(downloadConfig);
        }
    }
}
