package com.miuey.happytree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.core.atp.Binding;
import com.miuey.happytree.core.atp.Extraction;
import com.miuey.happytree.core.atp.Initialization;
import com.miuey.happytree.core.atp.PreValidation;
import com.miuey.happytree.core.validator.NoActiveSessionValidator;
import com.miuey.happytree.core.validator.NoDefinedSessionValidator;
import com.miuey.happytree.core.validator.NotDuplicatedSessionValidator;
import com.miuey.happytree.core.validator.NotNullArgValidator;

class TreeFactory {
	
	private static TreeFactory instance;
	private static ATPLifecycleFactory lifecycleFactory;
	private static ServiceFactory serviceFactory;
	private static CollectionFactory collectionFactory;
	private static MapFactory mapFactory;
	private static ServiceValidatorFactory serviceValidatorFactory;
	private static PipelineFactory pipelineFactory;
	private static IOFactory ioFactory;

	
	protected TreeFactory() {}
	
	
	static ATPLifecycleFactory lifecycleFactory() {
		if (lifecycleFactory == null) {
			lifecycleFactory = getInstance().new ATPLifecycleFactory();
		}
		return lifecycleFactory;
	}
	
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
	
	class ATPLifecycleFactory extends TreeFactory {
		ATPLifecycleFactory() {}
		
		<T> ATPLifecycle<T> createLifecycle(TreePipeline pipeline) {
			return new ATPLifecycle<>(pipeline);
		}
		
		<T> ATPPhase<T> initPreValidation() {
			return new PreValidation<>();
		}
		
		<T> ATPPhase<T> initExtraction() {
			return new Extraction<>();
		}
		
		<T> ATPPhase<T> initInitialization() {
			return new Initialization<>();
		}
		
		<T> ATPPhase<T> initBinding() {
			return new Binding<>();
		}
	}
	
	class ServiceFactory extends TreeFactory {
		ServiceFactory() {}

		TreeManagerCore createTreeManagerCore() {
			return new TreeManagerCore();
		}
		
		TreeTransactionCore createTreeTransaction(TreeManager manager) {
			return new TreeTransactionCore(manager);
		}
		
		TreeSessionCore createTreeSession(String identifier) {
			return new TreeSessionCore(identifier);
		}
		
		<T> TreeElementCore<T> createElement(Object id, Object parent) {
			return new TreeElementCore<>(id, parent);
		}
	}

	class CollectionFactory extends TreeFactory {
		CollectionFactory() {}
		
		<T> HashSet<T> createHashSet() {
			return new HashSet<>();
		}
		
		<T> List<T> createArrayList() {
			return new ArrayList<>();
		}
	}
	
	class MapFactory extends TreeFactory {
		MapFactory() {}
		
		<K, V> HashMap<K,V> createHashMap() {
			return new HashMap<>();
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
		
		TreeServiceValidator createNotNullArgValidator() {
			return new NotNullArgValidator();
		}
		
		TreeServiceValidator createNotDuplicatedSessionValidator() {
			return new NotDuplicatedSessionValidator();
		}
		
		TreeServiceValidator createNoDefinedSessionValidator() {
			return new NoDefinedSessionValidator();
		}
		
		TreeServiceValidator createNoActiveSessionValidator() {
			return new NoActiveSessionValidator();
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
