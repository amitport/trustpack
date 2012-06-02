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

import graphpack.INode;
import graphpack.NodeLocation;
import graphpack.SerializableEdge;
import graphpack.matching.Result;
import graphpack.matching.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * wraps a node - can be a trustor or a trustee of trust statements
 * @author amitport
 */
public class Entity {

	INode node;
	public Entity(INode node) {
		this.node = node;
	}
	
	/**
	 * make a trust statement from this entity another target node
	 * @param target the target node's location
	 * @param e the evaluation made by this node
	 */
	public void makeTS(NodeLocation target, Evaluation e){
		node.addOutgoingEdge(target, e);
	}

	/**
	 * get this entity's evaluations
	 * @return all evaluations made by this entity
	 */
	@SuppressWarnings("unchecked")
	public <T extends Evaluation> List<T> getEvaluations() {
		List<T> evals = new ArrayList<T>();
		graphpack.matching.ResultSet rs = node.traverse("-[e]->");
		for (graphpack.matching.Result r :rs.elementSet()){
			evals.add((T)((SerializableEdge)r.get("e")).payload);
		}
		return evals;
	}

	/**
	 * get evaluation that connect to this node
	 * @param string a path expression which starts at this node and matches `e' as the desired evaluations
	 * @param serviceName the target service
	 * @return matched evaluations
	 */
	@SuppressWarnings("unchecked")
	public <T extends Evaluation> List<T>  getEvaluations(String string, String serviceName) {
		List<T> evals = new ArrayList<T>();
		for (graphpack.matching.Result r :
			node.traverse(string+"?(_trg=={0})",new NodeLocation(serviceName,"","")).elementSet()){
			evals.add((T)((SerializableEdge)r.get("e")).payload);
		}
		return evals;
	}
	
	/**
	 * runs {@link Entity#getEvaluations(String, String)} and returns the first value
	 */
	public <T extends Evaluation> T  getEvaluation(String string, String serviceName) {
		return this.<T>getEvaluations(string,serviceName).get(0);
	}
	
	/**
	 * same as {@link Entity#getEvaluations(String, String)} except returns a {@link ResultSet}
	 */
	public ResultSet traverse(String string, String serviceName){
		ResultSet $ = node.traverse(string+"?(_trg=={0})",new NodeLocation(serviceName,"",""));
		for (Result r :	$.elementSet()){
			for (Entry<String, Object> e :r.map.entrySet()){
				Object v = e.getValue();
				if (v instanceof SerializableEdge){
					e.setValue(((SerializableEdge)v).payload);
				}
			}
		}
		return $;
	}

}
