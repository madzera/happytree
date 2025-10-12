package com.madzera.happytree.demo.model;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;

/**
 * The class <code>ObjectNotSerializedError</code> is a demo class that
 * represents an object without implementing serialization. This class simulates
 * an error because it does not implement {@link java.io.Serializable}.
 */
@Tree
public class ObjectNotSerializedError {
    @Id
    private Long id;
    @Parent
    private Long parentId;


    public ObjectNotSerializedError(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }


    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }
}
