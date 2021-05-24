# Notes on Spring Data Stores and Persistence 

**Entities** are used to negotiate between:
* The **object** representation of data in Java.
* The **table representation** of data in a database.

**Java Persistence API (JPA)**
A specification describing how to manage relational data.

**Hibernate**
An implementation of the JPA Specification. You can access **Hibernate’s documentation page** [here](https://hibernate.org/orm/).

**Entity Type** <=> **Table** in **Database**

**Value Type** <=> **Column** in **Table**

Specify inheritance strategies using the **@Inheritance** annotation on the parent class. The valid choices are **InheritanceType.SINGLE_TABLE**, **InheritanceType.JOINED**, and **InheritanceType.TABLE_PER_CLASS**.

**JOINED**
Creates a table for the parent class and each subclass. The subclass tables only have fields unique to their class. Supports polymorphic queries by UNIONing subclass tables. Uses the least space of the solutions that allow Not Null columns.

**TABLE_PER_CLASS**
Creates a table for the parent class and each subclass. The subclass tables have all fields from the parent class as well as fields unique to their class. Supports polymorphic queries by UNIONing subclass tables, but does not require any UNION to access superclass fields on non-polymorphic queries.

**@JSONView:** an annotation that filters which Entity data is visible to the Presentation layer

## Ways to Associate Data
* Value Types: Become single columns in containing Entity’s table.
* Embeddables: Add their attributes as columns in the containing Entity’s table.
* Entities: Become new tables which relate to a containing entity by a Join Column.


**Unidirectional:** Association specified on one side of the relationship only.
* Doesn't retrieve data you won’t use.
* Should use Set collection type for most efficient SQL.

You only need to specify the Entity on a single side of the relationship.

```
@Entity
public class Person {
   @Id
   @GeneratedValue
   private Long id;

   @OneToMany
   private List<Outfit> outfits;

   /* rest of class */
}
```

**Bidirectional:** Association specified on both sides of the relationship. Use **mappedBy** on the containing Entity side. Both classes have a reference to each other.
* Access both sides of relationship with a single query.
* Hibernate recommends for **@OneToMany**, because it allows the foreign key constraint to exist only on the table of the contained object.
  
```
@Entity
public class Person {
   @Id
   @GeneratedValue
   private Long id;

   @OneToMany(mappedBy = "person")
   private List<Outfit> outfits;

   /* rest of class */
}

@Entity
public class Outfit {
   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   private Person person;

   /* rest of class */
}
```

## **@JoinTable**
Many associations can be stored in a single **@JoinColumn** on one of the two entity tables, but you may also elect to store the relationship in a table designated for that purpose. **@ManyToMany** relationships must use a Join Table, and will automatically create one even if not specified.

To control the name of the table and its columns, you can use the **@JoinColumn** annotation.

```
@Entity
public class Person {
   @Id
   @GeneratedValue
   private Long id;

   @ManyToMany
   @JoinTable(
      name = "person_outfit",
      joinColumns = { 
		@JoinColumn(name = "person_id")},
      		inverseJoinColumns = { 
			@JoinColumn(name = "outfit_id")
		}
   )
   private List<Outfit> outfits;

   /* rest of class */
}
```

## **@ElementCollection**
You can use the **@ElementCollection**annotation to denote an association between a single Entity and a list of values (value types or a collection of Embeddables) that are not themselves Entities. This annotation lets you persist Lists of Embeddables or enums. These embeddables will be stored in a separate table, along with the id of the Entity in which they are contained.