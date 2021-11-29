package com.szhengzhu.util;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * @author Jehon Zeng
 * @param <K>
 */
public class ExpireSet<K> extends HashSet<K> {

    private static final long serialVersionUID = -1488591748185474774L;

    private Queue<KeyValue<K>> queue = new ConcurrentLinkedQueue<ExpireSet<K>.KeyValue<K>>();

    // 单位：秒
    private int period = 60;

    private Set<K> set = this;

    // 手动创建线程池
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2,
            new BasicThreadFactory.Builder().daemon(true).build());

    public ExpireSet() {
        // 可查看ScheduledExecutorService与Timer的对比
        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                KeyValue<K> kv = null;
                long currentTime = System.currentTimeMillis();
                while (true) {
                    kv = queue.peek();
                    if (kv != null && kv.value < currentTime) {
                        set.remove(kv.key);
                        queue.poll();
                    } else {
                        break;
                    }
                }
            }
        }, 1000 * 10, 1000 * 60, TimeUnit.MILLISECONDS);
    }

    public ExpireSet(int period) {
        this.period = period;
    }

    @Override
    public boolean add(K e) {
        queue.offer(new KeyValue<K>(e, System.currentTimeMillis() + 1000 * period));
        return super.add(e);
    }

    protected class KeyValue<KK> {
        KK key;
        long value;

        public KeyValue(KK key, long value) {
            this.key = key;
            this.value = value;
        }
    }
}
