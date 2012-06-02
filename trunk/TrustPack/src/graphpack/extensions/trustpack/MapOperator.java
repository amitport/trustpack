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

import java.util.ArrayList;
import java.util.List;

/**
 * maps values new values
 * @author amitport
 */
public abstract class MapOperator {
	
	/**
	 * maps a list to a new list
	 * @param evals input list
	 * @param f a function which maps a single value
	 * @return the mapped list
	 */
	public static <T extends Evaluation,S> List<S> map(List<T> evals, MapFunc<T,S> f){
		List<S> $ = new ArrayList<S>();
		for(T eval : evals){
			$.add(f.evaluate(eval));
		}
		return $;
	}
	
	
	/**
	 * a function which maps a single value to a new value
	 * @author amitport
	 *
	 * @param <T> input value
	 * @param <S> output value
	 */
	public static interface MapFunc<T,S>{
		public S evaluate(T o);
	}
}
