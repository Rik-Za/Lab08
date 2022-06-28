package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	
	private Graph<Airport,DefaultWeightedEdge> grafo;
	private Map<Integer,Airport> mappaAeroporti = dao.loadAllAirports();
	
	public String creaGrafo(int distanza) {
		this.grafo= new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<CoppiaAer> distanze = dao.getDistanzeMedie(distanza);
		
		for(CoppiaAer c: distanze) {
			this.grafo.addVertex(mappaAeroporti.get(c.getIdPartenza()));
			this.grafo.addVertex(mappaAeroporti.get(c.getIdArrivo()));
			/*this.grafo.addEdge(mappaAeroporti.get(c.getIdPartenza()), mappaAeroporti.get(c.getIdArrivo()));
			this.grafo.setEdgeWeight(mappaAeroporti.get(c.getIdPartenza()), mappaAeroporti.get(c.getIdArrivo()), c.getMedia());*/
			Graphs.addEdgeWithVertices(this.grafo, mappaAeroporti.get(c.getIdPartenza()), mappaAeroporti.get(c.getIdArrivo()), c.getMedia());
		}
		String result="";
		result+="Il numero di vertici e': "+this.grafo.vertexSet().size()+"\n";
		result+="Il numero di archi e': "+this.grafo.edgeSet().size()+"\n";
		for(CoppiaAer c: distanze)
			result+=mappaAeroporti.get(c.getIdPartenza()).getAirportName()+" ---- "+mappaAeroporti.get(c.getIdArrivo()).getAirportName()+" ---- "+c.getMedia()+"\n";
		
		
		
		
		
		return result;
	}

}
