/*
 * The MIT License
 *
 * Copyright (c) 2013, Magnus Sandberg
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.advancedqueue.sorter;

import hudson.ExtensionList;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Queue;
import hudson.model.Queue.LeftItem;

import java.util.ArrayList;
import java.util.List;

import jenkins.model.Jenkins;

import org.apache.tools.ant.ExtensionPoint;

/**
 * @author Magnus Sandberg
 * @since 2.0
 */
public abstract class SorterStrategy 
    extends ExtensionPoint implements Describable<SorterStrategy> {
        @Override
        public SorterStrategyDescriptor getDescriptor() {
            return (SorterStrategyDescriptor) Jenkins.getInstance().getDescriptorOrDie(getClass());
        }

        
	/**
	 * Called when a new {@link hudson.model.Item} enters the queue.
	 * 
	 * @param item
	 *            the {@link hudson.model.WaitingItem} or
	 *            {@link hudson.model.BuildableItem} that enters the queue
	 * @return the weight of the item in the queue, lower value will give sooner
	 *         start
	 */
	public abstract float onNewItem(Queue.Item item);

	/**
	 * Called when a {@link hudson.model.Item} leaves the queue and it is
	 * started.
	 * 
	 * @param item
	 *            the {@link hudson.model.LeftItem}
	 * @param weight
	 *            the weight assigned when the item entered the queue
	 */
	public void onStartedItem(LeftItem item, float weight) {
	}

	/**
	 * Called when a {@link hudson.model.Item} leaves the queue and it is
	 * canceled.
	 */
	public void onCanceledItem(LeftItem item) {
	};
        
        /**
         * Gets number of priority buckets to be used.
         * @return 
         */
        public abstract int getNumberOfPriorities();

        /**
         * Gets a default priority bucket to be used.
         * @return 
         */
        public abstract int getDefaultPriority();

        
	public static List<SorterStrategyDescriptor> getAllSorterStrategies() {
		ExtensionList<SorterStrategy> all = all();
		ArrayList<SorterStrategyDescriptor> strategies = new ArrayList<SorterStrategyDescriptor>(
				all.size());
		for (SorterStrategy prioritySorterStrategy : all) {
			strategies.add(prioritySorterStrategy.getDescriptor());
		}
		return strategies;
	}

	public static SorterStrategyDescriptor getSorterStrategy(String key) {
		List<SorterStrategyDescriptor> allSorterStrategies = getAllSorterStrategies();
		for (SorterStrategyDescriptor sorterStrategy : allSorterStrategies) {
			if (key.equals(sorterStrategy.getKey())) {
				return sorterStrategy;
			}
		}
		return null;
	}

	public static SorterStrategy getPrioritySorterStrategy(
			SorterStrategyDescriptor sorterStrategy) {
		ExtensionList<SorterStrategy> all = all();
		for (SorterStrategy prioritySorterStrategy : all) {
			if (prioritySorterStrategy.getDescriptor().getKey()
					.equals(sorterStrategy.getKey())) {
				return prioritySorterStrategy;
			}
		}
		return null;
	}

	/**
	 * All registered {@link SorterStrategy}s.
	 */
	public static ExtensionList<SorterStrategy> all() {
		return Jenkins.getInstance().getExtensionList(SorterStrategy.class);
	}  
}
