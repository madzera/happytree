package com.miuey.happytree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.miuey.happytree.core.validator.NotDuplicatedSessionValidator;
import com.miuey.happytree.core.validator.NotNullValidator;

class TreeFactory {
	
	private static TreeFactory instance;
	private static ServiceFactory serviceFactory;
	private static CollectionFactory collectionFactory;
	private static MapFactory mapFactory;
	private static ServiceValidatorFactory serviceValidatorFactory;
	private static PipelineFactory pipelineFactory;
	private static IOFactory ioFactory;

	
	protected TreeFactory() {}
	

	static ServiceFactory serviceFactory() {
		if (serviceFactory == null) {
			serviceFactory = getInstance().new ServiceFactory();
		}
		return serviceFactory;
	}
	
	static CollectionFactory collectionFactory() {
		if (collectionFactory == null) {
			collectionFactory = getInstance().new CollectionFactory();
		}
		return collectionFactory;
	}
	
	static MapFactory mapFactory() {
		if (mapFactory == null) {
			mapFactory = getInstance().new MapFactory();
		}
		return mapFactory;
	}
	
	static IOFactory ioFactory() {
		if(ioFactory == null) {
			ioFactory = getInstance().new IOFactory();
		}
		return ioFactory;
	}
	
	static ServiceValidatorFactory serviceValidatorFactory() {
		if (serviceValidatorFactory == null) {
			serviceValidatorFactory = getInstance().new 
					ServiceValidatorFactory();
		}
		return serviceValidatorFactory;
	}
	
	static PipelineFactory pipelineFactory() {
		if (pipelineFactory == null) {
			pipelineFactory = getInstance().new PipelineFactory();
		}
		return pipelineFactory;
	}
	
	class ServiceFactory extends TreeFactory {
		ServiceFactory() {}

		TreeTransactionCore createTreeTransaction() {
			return new TreeTransactionCore();
		}
		
		TreeSessionCore createTreeSession(String identifier) {
			return new TreeSessionCore(identifier);
		}
		
		<T> TreeElementCore<T> createElement(Object id, Object parent) {
			return new TreeElementCore<T>(id, parent);
		}
	}

	class CollectionFactory extends TreeFactory {
		CollectionFactory() {}
		
		<T> HashSet<T> createHashSet() {
			return new HashSet<T>();
		}
		
		<T> List<T> createArrayList() {
			return new ArrayList<T>();
		}
	}
	
	class MapFactory extends TreeFactory {
		MapFactory() {}
		
		<K, V> HashMap<K,V> createHashMap() {
			return new HashMap<K, V>();
		}
	}

	class IOFactory extends TreeFactory {
		IOFactory() {}
		
		Properties createPropertiesFile() {
			return new Properties();
		}
	}
	
	class ServiceValidatorFactory extends TreeFactory {
		ServiceValidatorFactory() {}
		
		TreeServiceValidator createNotNullInputValidator() {
			return new NotNullValidator();
		}
		
		TreeServiceValidator createNotDuplicatedSessionValidator() {
			return new NotDuplicatedSessionValidator();
		}
	}

	class PipelineFactory extends TreeFactory {
		PipelineFactory() {}
		
		TreePipeline createPipelineValidator() {
			return new TreePipeline();
		}
	}
	
	protected static TreeFactory getInstance() {
		if (instance == null) {
			instance = new TreeFactory();
		}
		return instance;
	}
}
