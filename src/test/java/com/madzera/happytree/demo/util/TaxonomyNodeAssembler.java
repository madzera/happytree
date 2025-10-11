package com.madzera.happytree.demo.util;

import java.util.ArrayList;
import java.util.List;

import com.madzera.happytree.demo.model.TaxonomyNode;

public class TaxonomyNodeAssembler {

	public static List<TaxonomyNode> getTaxonomyNodeList() {
		TaxonomyNode dispatches = new TaxonomyNode();
		TaxonomyNode y2020 = new TaxonomyNode();
		TaxonomyNode july = new TaxonomyNode();
		TaxonomyNode august = new TaxonomyNode();
		TaxonomyNode september = new TaxonomyNode();
		
		july.setLevel(2);
		july.setTaxonomyID(54393);
		july.setParentNodeID(4002);
		july.setNodeName("July");
		
		august.setLevel(2);
		august.setTaxonomyID(98474);
		august.setParentNodeID(4002);
		august.setNodeName("August");
		
		september.setLevel(2);
		september.setTaxonomyID(93549);
		september.setParentNodeID(4002);
		september.setNodeName("September");
		
		y2020.setLevel(1);
		y2020.setTaxonomyID(4002);
		y2020.setParentNodeID(92);
		y2020.setNodeName("2020");
		
		dispatches.setLevel(0);
		dispatches.setTaxonomyID(92);
		dispatches.setParentNodeID(null);
		dispatches.setNodeName("Dispatches");
		
		List<TaxonomyNode> nodesList = new ArrayList<>();
		nodesList.add(july);
		nodesList.add(september);
		nodesList.add(dispatches);
		nodesList.add(y2020);
		nodesList.add(august);
		
		return nodesList;
	}
}
