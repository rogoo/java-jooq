/*
 * This file is generated by jOOQ.
 */
package br.rosa.rjooq.generated.tables;


import br.rosa.rjooq.generated.Keys;
import br.rosa.rjooq.generated.Library;
import br.rosa.rjooq.generated.tables.Post.PostPath;
import br.rosa.rjooq.generated.tables.records.AuthorRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Author extends TableImpl<AuthorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>library.author</code>
     */
    public static final Author AUTHOR = new Author();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthorRecord> getRecordType() {
        return AuthorRecord.class;
    }

    /**
     * The column <code>library.author.id</code>.
     */
    public final TableField<AuthorRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>library.author.first_name</code>.
     */
    public final TableField<AuthorRecord, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>library.author.last_name</code>.
     */
    public final TableField<AuthorRecord, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(255), this, "");

    private Author(Name alias, Table<AuthorRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Author(Name alias, Table<AuthorRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>library.author</code> table reference
     */
    public Author(String alias) {
        this(DSL.name(alias), AUTHOR);
    }

    /**
     * Create an aliased <code>library.author</code> table reference
     */
    public Author(Name alias) {
        this(alias, AUTHOR);
    }

    /**
     * Create a <code>library.author</code> table reference
     */
    public Author() {
        this(DSL.name("author"), null);
    }

    public <O extends Record> Author(Table<O> path, ForeignKey<O, AuthorRecord> childPath, InverseForeignKey<O, AuthorRecord> parentPath) {
        super(path, childPath, parentPath, AUTHOR);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class AuthorPath extends Author implements Path<AuthorRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> AuthorPath(Table<O> path, ForeignKey<O, AuthorRecord> childPath, InverseForeignKey<O, AuthorRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private AuthorPath(Name alias, Table<AuthorRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public AuthorPath as(String alias) {
            return new AuthorPath(DSL.name(alias), this);
        }

        @Override
        public AuthorPath as(Name alias) {
            return new AuthorPath(alias, this);
        }

        @Override
        public AuthorPath as(Table<?> alias) {
            return new AuthorPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Library.LIBRARY;
    }

    @Override
    public Identity<AuthorRecord, Integer> getIdentity() {
        return (Identity<AuthorRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<AuthorRecord> getPrimaryKey() {
        return Keys.KEY_AUTHOR_PRIMARY;
    }

    private transient PostPath _post;

    /**
     * Get the implicit to-many join path to the <code>library.post</code> table
     */
    public PostPath post() {
        if (_post == null)
            _post = new PostPath(this, null, Keys.FK_POST_AUTHOR.getInverseKey());

        return _post;
    }

    @Override
    public Author as(String alias) {
        return new Author(DSL.name(alias), this);
    }

    @Override
    public Author as(Name alias) {
        return new Author(alias, this);
    }

    @Override
    public Author as(Table<?> alias) {
        return new Author(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(String name) {
        return new Author(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(Name name) {
        return new Author(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Author rename(Table<?> name) {
        return new Author(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author where(Condition condition) {
        return new Author(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Author where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Author where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Author where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Author where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Author whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
