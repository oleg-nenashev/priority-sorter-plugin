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
package jenkins.advancedqueue.sorter.strategy;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.Queue.WaitingItem;
import jenkins.advancedqueue.PriorityConfiguration;
import jenkins.advancedqueue.sorter.SorterStrategy;
import jenkins.advancedqueue.sorter.SorterStrategyType;
import jenkins.advancedqueue.strategy.Messages;

/**
 * @author Magnus Sandberg
 * @since 2.0
 */
@Extension
public class AbsoluteStrategy extends SorterStrategy {

	private final SorterStrategyType strategy = new SorterStrategyType("ABSOLUTE", Messages.SorterStrategy_ABSOLUTE_displayName());
	
	public SorterStrategyType getSorterStrategy() {
		return strategy;
	}
	
	public float onNewItem(WaitingItem item) {
		return PriorityConfiguration.get().getPriority(item);
	}

}