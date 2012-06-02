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

import graphpack.Edge.Payload;

/**
 * an evaluation contained within some trust statement
 * @author amitport
 */
public interface Evaluation extends Payload{
	
	/**
	 * stores two evaluations together
	 * @author amitport
	 */
	public static class Cons<T1,T2> implements Evaluation {
		private static final long serialVersionUID = 8007980303088550213L;
		
		public T1 _1;
		public T2 _2;
		public Cons(T1 _1, T2 _2){
			this._1 = _1;
			this._2 = _2;
		}
	}
	
	/**
	 * stores a balance of negative an positive experience
	 * @author amitport
	 */
	public static class Balance implements Evaluation {
		private static final long serialVersionUID = 8007980303088550213L;
		
		public long balance;
		public Balance(long pos, long neg){
			balance = pos - neg;
		}
		
		public void update(long pos, long neg){
			balance = pos - neg;
		}
	}
	
	/**
	 * stores a evaluation that consists of a single double value
	 * @author amitport
	 */
	public static class Normalized implements Evaluation {
		private static final long serialVersionUID = 8007980303088550213L;
		
		public double val;
		public Normalized(double val){
			this.val = val;
		}
		
		public void update(double val){
			this.val = val;
		}
	}
}
