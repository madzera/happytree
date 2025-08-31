package com.madzera.happytree.demo.model;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;

/**
 * The class <code>ObjectNoGetterError</code> is a demo class that represents an
 * object without getters methods. This class simulates reflection error because
 * there is no getters for its attributes.
 */
@Tree
public class ObjectNoGetterError {
    @Id
    private Long id;
    @Parent
    private Long parendId;


    public Long getIdError() {
        return id;
    }
    public Long getParentIdError() {
        return parendId;
    }
}
