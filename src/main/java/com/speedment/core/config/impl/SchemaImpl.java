/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.core.config.impl;

import com.speedment.core.config.impl.aspects.ParentHelper;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.Schema;
import com.speedment.api.config.Table;
import com.speedment.api.config.aspects.Parent;
import com.speedment.api.config.parameters.ColumnCompressionType;
import com.speedment.api.config.parameters.FieldStorageType;
import com.speedment.api.config.parameters.StorageEngineType;
import com.speedment.core.config.impl.utils.ConfigUtil;
import com.speedment.util.Cast;
import groovy.lang.Closure;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class SchemaImpl extends AbstractNamedConfigEntity implements Schema, ParentHelper<Table> {

    private Dbms parent;
    private final ChildHolder children;
    private boolean defaultSchema;
    private String schemaName;
    private String catalogName;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;

    public SchemaImpl() {
        children = new ChildHolder();
    }

    @Override
    protected void setDefaults() {
        setFieldStorageType(FieldStorageType.WRAPPER);
        setColumnCompressionType(ColumnCompressionType.NONE);
        setStorageEngineType(StorageEngineType.ON_HEAP);
    }

    @Override
    public Boolean isDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public void setDefaultSchema(Boolean defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        this.fieldStorageType = fieldStorageType;
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        this.columnCompressionType = columnCompressionType;
    }

    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        this.storageEngineType = storageEngineType;
    }

    @Override
    public Optional<String> getCatalogName() {
        return Optional.ofNullable(catalogName);
    }

    @Override
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public Optional<String> getSchemaName() {
        if (schemaName == null) {
            return Optional.ofNullable(getName());
        }
        return Optional.of(schemaName);
    }

    @Override
    public void setSchemaName(String schemaName) {
        this.catalogName = schemaName;
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.orFail(parent, Dbms.class);
    }

    @Override
    public Optional<Dbms> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Table addNewTable() {
        final Table e = Table.newTable();
        add(e);
        return e;
    }
    
    @Override
    public Table table(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewTable);
    }
}