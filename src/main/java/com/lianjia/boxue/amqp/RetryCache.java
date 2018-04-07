package com.lianjia.boxue.amqp;

/*import com.littlersmall.rabbitmqaccess.common.Constants;
import com.littlersmall.rabbitmqaccess.common.DetailRes;*/

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetryCache {
	// private MessageSender sender;
	private boolean stop = false;
	private Map<String, MessageWithTime> map = new ConcurrentHashMap<>();
	private AtomicLong id = new AtomicLong();
	@Autowired
	ExamMessageSender sender;
	Logger log = LoggerFactory.getLogger(this.getClass());

	private static class MessageWithTime {
		long time;
		String message;

		public MessageWithTime(long time, String message) {
			this.time = time;
			this.message = message;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@PostConstruct
	public void init() {
		this.startRetry();
	}

	public String generateId() {
		return "" + id.incrementAndGet();
	}

	public void add(String id, String message) {
		map.put(id, new MessageWithTime(System.currentTimeMillis(), message));
	}

	public void del(String id) {
		map.remove(id);
	}

	private void startRetry() {
		new Thread(() -> {
			while (!stop) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				long now = System.currentTimeMillis();

				for (Map.Entry<String, MessageWithTime> entry : map.entrySet()) {
					MessageWithTime messageWithTime = entry.getValue();

					if (null != messageWithTime) {
						if (messageWithTime.getTime() + 3 * 60 * 1000 < now) {
							log.info("send message failed after 3 min " + messageWithTime);
							del(entry.getKey());
						} else if (messageWithTime.getTime() + 60 * 1000 < now) {
							log.info("resent failed msg.");
							DetailRes detailRes = sender.sendExamData(messageWithTime.getMessage(), false, entry.getKey());
							/*
							 * detailRes.isSuccess()不能确保消息发送成功，confirm才能。下面代码有误。
							 */
							/*if (detailRes.isSuccess()) {
								del(entry.getKey());
							}*/
						}
					}
				}
			}
		}).start();
	}
}
