package com.madzera.happytree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.core.atp.ATPFactory;
import com.madzera.happytree.exception.TreeException;

/*
 * Abstract Factory used for HappyTree API.
 */
class TreeFactory {
	
	private static TreeFactory instance;
	private static ATPLifecycleFactory lifecycleFactory;
	private static ServiceFactory serviceFactory;
	private static CollectionFactory collectionFactory;
	private static MapFactory mapFactory;
	private static ValidatorFactory validatorFactory;
	private static FacadeFactory facadeFactory;
	private static PipelineFactory pipelineFactory;
	private static ExceptionFactory exceptionFactory;
	private static UtilFactory utilFactory;
	private static JsonFactory jsonFactory;
	private static XmlFactory xmlFactory;
	private static IoFactory ioFactory;
	
	
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
	
	static ValidatorFactory validatorFactory() {
		if (validatorFactory == null) {
			validatorFactory = getInstance().new ValidatorFactory();
		}
		return validatorFactory;
	}

	static FacadeFactory facadeFactory() {
		if (facadeFactory == null) {
			facadeFactory = getInstance().new FacadeFactory();
		}
		return facadeFactory;
	}
	
	static ExceptionFactory exceptionFactory() {
		if (exceptionFactory == null) {
			exceptionFactory = getInstance().new ExceptionFactory();
		}
		return exceptionFactory;
	}
	
	static PipelineFactory pipelineFactory() {
		if (pipelineFactory == null) {
			pipelineFactory = getInstance().new PipelineFactory();
		}
		return pipelineFactory;
	}
	
	static UtilFactory utilFactory() {
		if (utilFactory == null) {
			utilFactory = getInstance().new UtilFactory();
		}
		return utilFactory;
	}

	static JsonFactory jsonFactory() {
		if (jsonFactory == null) {
			jsonFactory = getInstance().new JsonFactory();
		}
		return jsonFactory;
	}

	static XmlFactory xmlFactory() {
		if (xmlFactory == null) {
			xmlFactory = getInstance().new XmlFactory();
		}
		return xmlFactory;
	}

	static IoFactory ioFactory() {
		if (ioFactory == null) {
			ioFactory = getInstance().new IoFactory();
		}
		return ioFactory;
	}
	
	
	class ATPLifecycleFactory extends ATPFactory {
		ATPLifecycleFactory() {}
		
		<T> ATPLifecycle<T> createLifecycle(TreePipeline pipeline) {
			return new ATPLifecycle<>(pipeline);
		}
		
		<T> ATPPhase<T> initPreValidation() {
			return getPhaseInstance(ATPPhaseInstance.PRE_VALIDATION);
		}
		
		<T> ATPPhase<T> initExtraction() {
			return getPhaseInstance(ATPPhaseInstance.EXTRACTION);
		}
		
		<T> ATPPhase<T> initInitialization() {
			return getPhaseInstance(ATPPhaseInstance.INITIALIZATION);
		}
		
		<T> ATPPhase<T> initBinding() {
			return getPhaseInstance(ATPPhaseInstance.BINDING);
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
		
		TreeSessionCore createTreeSession(String identifier, Class<?> typeTree) {
			return new TreeSessionCore(identifier, typeTree);
		}
		
		<T> TreeElementCore<T> createElement(Object id, Object parent,
				T wrappedNode, TreeSession session) {
			return new TreeElementCore<>(id, parent, wrappedNode, session);
		}

		<T> TreeElementCore<T> createElement(TreeSession session) {
			return new TreeElementCore<>(null, null, null, session);
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

	class ValidatorFactory extends TreeFactory {
		ValidatorFactory() {}
		
		TreeSessionValidator createSessionValidator(TreeManager manager) {
			return new TreeSessionValidator(manager);
		}
		
		TreeMandatoryValidator createMandatoryValidator() {
			return new TreeMandatoryValidator(null);
		}
		
		TreeCutValidator createCutValidator(TreeManager manager) {
			return new TreeCutValidator(manager);
		}
		
		TreeCopyValidator createCopyValidator(TreeManager manager) {
			return new TreeCopyValidator(manager);
		}
		
		TreeRemoveValidator createRemoveValidator(TreeManager manager) {
			return new TreeRemoveValidator(manager);
		}
		
		TreePersistValidator createPersistValidator(TreeManager manager) {
			return new TreePersistValidator(manager);
		}
		
		TreeUpdateValidator createUpdateValidator(TreeManager manager) {
			return new TreeUpdateValidator(manager);
		}
	}

	class FacadeFactory extends TreeFactory {
		FacadeFactory() {}
		
		TreeValidatorFacade createValidatorFacade(TreeManager manager) {
			return new TreeValidatorFacade(manager);
		}
	}
	
	class ExceptionFactory extends TreeFactory {
		ExceptionFactory() {}
		
		IllegalArgumentException createRuntimeException(String message) {
			return new IllegalArgumentException(message);
		}
		
		Exception createException() {
			return new Exception();
		}
		
		TreeException createTreeException(String message) {
			return new TreeException(message);
		}
	}
	
	class PipelineFactory extends TreeFactory {
		PipelineFactory() {}
		
		TreePipeline createPipelineValidator() {
			return new TreePipeline();
		}
	}
	
	class UtilFactory extends TreeFactory {
		UtilFactory() {
		}

		Cache createCacheSession() {
			return new Cache();
		}
	}
	
	class JsonFactory extends TreeFactory {
		JsonFactory() {
		}

		ObjectMapper createObjectMapper() {
			return new ObjectMapper();
		}
	}
	
	class XmlFactory extends TreeFactory {
		XmlFactory() {
		}

		XmlMapper createXmlMapper() {
			return new XmlMapper();
		}
	}
	
	class IoFactory extends TreeFactory {
		IoFactory() {
		}

		ObjectOutputStream createObjectOutputStream(OutputStream outputStream)
				throws IOException {
			return new ObjectOutputStream(outputStream);
		}

		ByteArrayOutputStream createByteArrayOutputStream() {
			return new ByteArrayOutputStream();
		}

		ObjectInputStream createObjectInputStream(
				ByteArrayInputStream byteArrayInputStream) throws IOException {
			return new ObjectInputStream(byteArrayInputStream);
		}

		ByteArrayInputStream createByteArrayInputStream(byte[] byteArray) {
			return new ByteArrayInputStream(byteArray);
		}
	}
	

	protected static TreeFactory getInstance() {
		if (instance == null) {
			instance = new TreeFactory();
		}
		return instance;
	}
}
