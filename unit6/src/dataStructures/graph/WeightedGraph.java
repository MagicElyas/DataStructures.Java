package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class WeightedGraph<V> implements WGraph<V> {
	Set <V> vertices;
	Dictionary <Tuple2<V,V>,Integer> aristas;
	
	public WeightedGraph(){
		vertices=new HashSet<>();
		aristas=new HashDictionary<>();
	}
	
	public void addVertex(V v){
		vertices.insert(v);
	}
	
	public void addEdge(V src, V fin,int peso){
		if(!vertices.isElem(src))
			throw new GraphException("vertex "+src+" is not in graph");
		if(!vertices.isElem(fin))
			throw new GraphException("vertex "+fin+" is not in graph");
		
		Tuple2<V,V> aux=new Tuple2<>(src,fin);
		Tuple2<V,V> aux2=new Tuple2<>(fin,src);
		aristas.insert(aux, peso);
		aristas.insert(aux2, peso);
		
	}
	public void deleteEdge(V src, V fin){
		Tuple2<V,V> aux=new Tuple2<>(src,fin);
		Tuple2<V,V> aux2=new Tuple2<>(fin,src);
		aristas.delete(aux);
		aristas.delete(aux2);
	}
	
	public void deleteVertex(V v){
		vertices.delete(v); //Borramos el vereice
		//borramos las aristas
		for( Tuple2<V,V> k : aristas.keys() ){
			if(k._1().equals(v)|| k._2().equals(v)){
				aristas.delete(k);
			}
		}
	}
	
	public Set<V> vertices(){
		Set<V>res=new HashSet<>();
		for(V v:vertices){
			res.insert(v);
		}
		
		
		return res;
	}
	
	public Dictionary <Tuple2<V,V>,Integer> aristas(){
		Dictionary <Tuple2<V,V>,Integer> res=new HashDictionary<>();
		for(Tuple2<Tuple2<V,V>,Integer> v: aristas.keysValues()){
			res.insert(v._1(), v._2());
		}
		
		
		return res;
	}
	
	public int numVertices(){
		return vertices.size();
	}
	
	public int numEdges(){
		return aristas.size()/2;
	}
	
	public Dictionary<V,Integer> successors(V v){
		Dictionary<V,Integer> res=new HashDictionary<>();
		for(Tuple2<V,V> k: aristas.keys()){
			if(k._1().equals(v)){
				res.insert(k._2(), aristas.valueOf(k));
			}
		}
		
		return res;
	}
	public void updateEdges(Tuple2<V,Integer> t, int n){
		V ver=t._1();
		
		for(Tuple2<Tuple2<V,V>, Integer> k : aristas.keysValues()){
			if(k._1()._1().equals(ver) || k._1()._2().equals(ver)){
				int nuevo=k._2()+n;
				deleteEdge(k._1()._1(), k._1()._2());
				addEdge(k._1()._1(), k._1()._2(),nuevo);
			}
		}
		
	}
	
}
