package com.sgen.kafkastreams.app.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class FixedPriorityQueue<T> {
	private TreeSet<T> inner;
	private int maxSize;

	public FixedPriorityQueue() {
		// TODO Auto-generated constructor stub
	}

	public FixedPriorityQueue(Comparator<T> comparator, int maxSize) {
		this.inner = new TreeSet<>(comparator);
		this.maxSize = maxSize;
	}

	public FixedPriorityQueue<T> add(T element) {
		this.inner.add(element);
		if (this.inner.size() > maxSize) {
			this.inner.pollFirst();
		}
		return this;
	}

	public FixedPriorityQueue<T> remove(T element) {
		if (this.inner.contains(element)) {
			this.inner.remove(element);
		}
		return this;
	}

	public Iterator<T> iterate() {
		return this.inner.iterator();
	}

	public TreeSet<T> getInner() {
		return inner;
	}

	public void setInner(TreeSet<T> inner) {
		this.inner = inner;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public String toString() {
		return "FixedPriorityQueue{queueContent=" + inner + "}";
	}

}
