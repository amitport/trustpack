/*******************************************************************************
 * Copyright 2012 Amit Portnoy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package graphpack.extensions.trustpack;


import graphpack.extensions.trustpack.MapOperator.MapFunc;

import java.util.List;

/**
 * contains aggregation operators
 * @author amitport
 */
public abstract class AggregateOperator {

	/**
	 * aggregate values of a list 
	 * @param vals the list to aggregate
	 * @param mapF a function which maps the value of the list
	 * @param zero an initial value for the aggregation
	 * @param f a function that `adds' a new value to the aggregation
	 * @return the aggregated value
	 */
	public static <A,B,C> C aggr(
			List<A> vals, MapFunc<A,B> mapF, C zero, AgrrFunc<B,C> f){
		C aggregation = zero;
		for(A val : vals){
			aggregation = f.evaluate(mapF.evaluate(val),aggregation);
		}
		return aggregation;
	}
	
	/**
	 * a function which `add' a value to a given aggregation
	 * @author amitport
	 *
	 * @param <B> input values' type
	 * @param <C> aggregation's result type
	 */
	public static interface AgrrFunc<B,C>{
		/**
		 * `add' a value
		 * @param o1 value to `add'
		 * @param o2 current aggregation
		 * @return o1 `added' to o2
		 */
		public C evaluate(B o1, C o2);
	}
	
	/**
	 * a sum aggregation
	 * @author amitport
	 */
	public static class Sum{
		
		/**
		 * sums up values
		 * @param vals values to sum
		 * @param mapF maps original value
		 * @return sum of the values
		 */
		public static <A,B extends Number> Double aggr(List<A> vals, MapFunc<A,B> mapF){
			return AggregateOperator.aggr(vals, mapF, 0.0, 
					new AgrrFunc<B,Double>() {
				@Override
				public Double evaluate(B o1, Double o2) {
					return o1.doubleValue() + o2.doubleValue();
				}
			});
		}
	}
}
